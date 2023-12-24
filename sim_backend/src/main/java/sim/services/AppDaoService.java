/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.services;

import org.checkerframework.checker.units.qual.C;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import sim.data.Invoice;
import sim.data.domain.SimcardSearchModel;
import sim.data.domain.utils.MessageReturnPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.ParsedContract;
import sim.data.pg.tables.dbo.Contract;
import sim.data.pg.tables.dbo.Simcard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import sim.data.domain.InvoiceSearchModel;
import sim.exceptions.BillingException;

@Repository("appDaoService")
@Slf4j
@Transactional
public class AppDaoService extends BaseDaoService {

    @Autowired private IItemDao<Simcard, Integer> simcardDao;
    @Autowired private IItemDao<ParsedContract, String> parsedContractDao;
    @Autowired private IItemDao<Contract, Integer> contractDao;

    public MessageReturnPojo test() {
        try {
                return new MessageReturnPojo(0, "Успішно виконано");
            } catch (Exception ex) {
                return new MessageReturnPojo(-1, ex.getMessage());
        }
    }

//-----------------------------------------------------------------------

    public List<Simcard> getAllSims(){
        return simcardDao.list("Simcard.findAll");
    }

//-----------------------------------------------------------------------

    public List<Simcard> getSims(SimcardSearchModel model){

        DetachedCriteria crit = DetachedCriteria.forClass(Simcard.class);

        if (model.getFilialid() != null) {
            crit.add(Restrictions.eq("filialid", model.getFilialid()));
        }

        if (model.getSimcardid() != null) {
            crit.add(Restrictions.eq("simcardid", model.getSimcardid()));
        }

        if (model.getGrupaid() != null){
            crit.add(Restrictions.eq("grupaid", model.getGrupaid()));
        }

        if (model.getOfficialrankid() != null){
            crit.add(Restrictions.eq("officialrankid", model.getOfficialrankid()));
        }

        if (model.getTariffnametarifid() != null){
            crit.add(Restrictions.eq("tariffnametarifid", model.getTariffnametarifid()));
        }

        if (model.getAddressid() != null){
            crit.add(Restrictions.eq("addressid", model.getAddressid()));
        }

        if (model.getPhonenumber() != null && !model.getPhonenumber().isEmpty()) {
            if (model.getPhonenumber().contains("*")) {
                crit.add(Restrictions.like("phonenumber", model.getPhonenumber().replaceAll("\\*", "\\%")).ignoreCase());
            } else {
                crit.add(Restrictions.like("phonenumber", "%" + model.getPhonenumber() + "%").ignoreCase());
            }
        }

        if (model.getDu() != null && !model.getDu().isEmpty()) {
            crit.add(Restrictions.eq("du", model.getDu()));
        }

        if (model.getMp() != null && !model.getMp().isEmpty()) {
            crit.add(Restrictions.eq("mp", model.getMp()));
        }

        if (model.getNote1() != null && !model.getNote1().isEmpty()) {
            crit.add(Restrictions.eq("note1", model.getNote1()));
        }

        if (model.getPeriodbegin() != null) {
            crit.add(Restrictions.ge("periodbegin", model.getPeriodbegin()));
        }

        if (model.getPeriodend() != null) {
            crit.add(Restrictions.le("periodend", model.getPeriodend()));
        }

        return simcardDao.list(crit);
    }

//-----------------------------------------------------------------------

    public MessageReturnPojo simDelete(Integer id) throws Exception {
        simcardDao.deleteItem(id);
        return new MessageReturnPojo(1,"Item was delete");
    }

//-----------------------------------------------------------------------

    public MessageReturnPojo simCreate(Simcard model){
        SimcardSearchModel simcardSearchModel = SimcardSearchModel.builder()
                                                     .phonenumber(model.getPhonenumber())
                                                     .build();

        if(!getSims(simcardSearchModel).isEmpty())
            throw new BillingException();

        try {
            simcardDao.create(model);
            return new MessageReturnPojo(1, "Create successful");
        }catch (Exception e){
            return new MessageReturnPojo(0, "Cant create");
        }
    }

//-----------------------------------------------------------------------

    public MessageReturnPojo updateSim(Simcard model) {
        try {
            simcardDao.update(model);
            return new MessageReturnPojo(0, "Update was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

//-----------------------------------------------------------------------

    public MessageReturnPojo uploadFile(InputStream inputStream, Integer date){
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("windows-1251"))).lines()) {
            AtomicBoolean fl = new AtomicBoolean(false);
            AtomicReference<ParsedContract> parsedContact = new AtomicReference<>(new ParsedContract());
            stream.forEach(line->{

                if (line.isEmpty()){
                    if (!parsedContact.get().isNull()){
                        System.out.println("--------------------------------------------");
                        System.out.println(parsedContact);
                        System.out.println(date);
                        for (int i = 0; i < parsedContact.get().getInvoiceList().size(); i++){
                            ParsedContract instanceParsedContract = new ParsedContract();

                            instanceParsedContract.setContractnumber(parsedContact.get().getContractnumber());
                            instanceParsedContract.setPhonenumber(parsedContact.get().getPhonenumber());
                            instanceParsedContract.setPackagename(parsedContact.get().getPackagename());
                            instanceParsedContract.setInvoicename(parsedContact.get().getInvoiceList().get(i).getInvoicePoint());
                            instanceParsedContract.setNum1(parsedContact.get().getInvoiceList().get(i).getNum1());
                            instanceParsedContract.setNum2(parsedContact.get().getInvoiceList().get(i).getNum2());
                            instanceParsedContract.setNum3(parsedContact.get().getInvoiceList().get(i).getNum3());
                            instanceParsedContract.setPeriod(date);

                            parsedContractDao.create(instanceParsedContract);
                        }

                    }
                    parsedContact.set(new ParsedContract());

                    fl.set(false);
                }


                if(line.startsWith("Контракт №")){
                    contractInfo(line, parsedContact.get());
                    fl.set(true);
                    return;
                }

                if(line.startsWith("Тарифний Пакет")){
                    packageInfo(line, parsedContact.get());
                    return;
                }

                if(fl.get()){
                    Invoice invoice = parse(line);
                    if (invoice != null){
                        parsedContact.get().addInvoice(invoice);
                    }
                }
            });

        }catch (Exception e){
            e.getMessage();
        }

        return new MessageReturnPojo(1, "");
    }

    public void contractInfo(String s, ParsedContract pc){
        Pattern pattern = Pattern.compile("(\\D+)(?<contract>\\d*)(\\D+)(?<phonenumber>\\d*)");
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            pc.setContractnumber(m.group("contract"));
            pc.setPhonenumber(m.group("phonenumber"));
        }

    }

    public void packageInfo(String s, ParsedContract pc){
        Pattern pattern = Pattern.compile("(.+\\:)(?<packagename>.+)");
        Matcher m = pattern.matcher(s);

        if (m.find()){
            pc.setPackagename(m.group("packagename"));
        }
    }

    private  Invoice parse(String text){

        Pattern pattern = Pattern.compile("(.+\\:)(?<nums>.+)");
        Matcher m = pattern.matcher(parsingPrepare(text));
        if (m.find()) {
            BigDecimal[] nums = Stream.of(m.group("nums").split(","))
                    .map(item -> {
                        try {
                            return new BigDecimal(item);
                        } catch (NumberFormatException ex) {
                            return null;
                        }
                    })
                    .toArray(BigDecimal[]::new);

            Invoice invoice = new Invoice();
            invoice.setInvoicePoint(m.group(1).substring(0, m.group(1).length() - 1));
            invoice.setNum1(nums[0]);

            if (nums.length > 1) {
                invoice.setNum2(nums[1]);
                invoice.setNum3(nums[2]);
            }


            return invoice;

        } else {

            return null;
        }
    }

    private String parsingPrepare(String s){

        String res = s.replaceAll("\"", "")
                .replace(":,", ":")
                .replace(",,,", ":,,");

        //number of ","
        if(res.chars().filter(ch -> ch == ',').count()==1){
            res = res.replace(",", ":");
        }

        return res;

    }

//-----------------------------------------------------------------------

    public List<ParsedContract> invoiceSearch(InvoiceSearchModel model){
        
        DetachedCriteria dc = DetachedCriteria.forClass(ParsedContract.class);

        if (model.getPhonenumber() != null){
            if (model.getPhonenumber().contains("*")){
                dc.add(Restrictions.like("phonenumber", model.getPhonenumber().replaceAll("\\*", "\\%")));
            }else{
                dc.add(Restrictions.like("phonenumber", "%" + model.getPhonenumber() + "%").ignoreCase());
            }
//            dc.add(Restrictions.eq("phonenumber", model.getPhonenumber()));
        }
        if (model.getPeriod() != null){
            dc.add(Restrictions.eq("period", model.getPeriod()));
        }

        return parsedContractDao.list(dc);
    }

//-----------------------------------------------------------------------

    public List<Contract> getContracts(){
        return contractDao.list("Contract.findAll");
    }
    public List<Contract> getContract(Contract model){
        DetachedCriteria criteria = DetachedCriteria.forClass(Contract.class);

        if(model.getContractid() != null){
            criteria.add(Restrictions.eq("contractid", model.getContractid()));
        }

        return contractDao.list(criteria);
    }


    public List<Contract> contractSearch(Contract model) {
        DetachedCriteria crit = DetachedCriteria.forClass(Contract.class);

        if (model.getSimcardid() != null) {
            crit.add(Restrictions.eq("contractid", model.getSimcardid()));
        }

//        if (model.getTabnumber() != null && !model.getTabnumber().isEmpty()) {
//            crit.add(Restrictions.eq("tabnumber", model.getTabnumber()));
//            if (model.getTabnumber().contains("*")) {
//                crit.add(Restrictions.like("tabnumber", model.getTabnumber().replaceAll("\\*", "\\%")).ignoreCase());
//            } else {
//                crit.add(Restrictions.like("tabnumber", "%" + model.getTabnumber() + "%").ignoreCase());
//            }
//        }


        return contractDao.list(crit);
    }

    public Contract createContract(Contract model){
        if (!getContract(model).isEmpty())
            throw new BillingException();

        return contractDao.create(model);
    }

    public MessageReturnPojo updateContract(Contract model) {
        try {
            contractDao.update(model);
            return new MessageReturnPojo(0, "Update was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public MessageReturnPojo deleteContract(Integer id){
        try {
            contractDao.deleteItem(id);
            return new MessageReturnPojo(0, "Item was deleted successful!");
        } catch (NullPointerException npe) {
            throw new BillingException(-id, "Error: Item does not exist");
        } catch (Exception e) {
            throw new BillingException(-500, e.getMessage());
        }
    }
}







