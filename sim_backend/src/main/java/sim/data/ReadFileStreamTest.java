/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.ParsedContract;
import sim.services.BaseDaoService;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 *
 * @author dit51
 */
@Repository("testDaoService")
@Slf4j
@Transactional
public class ReadFileStreamTest extends BaseDaoService {

    @Autowired private static IItemDao<ParsedContract, String> itemDao;

    public static void sdad() {

//        String fileName = "/home/dit51/Downloads/in.csv";
        String fileName = "C:\\Users\\dit_practice\\Desktop\\in2.csv";

//        System.out.println("File: " + fileName);

        //Charset.availableCharsets().entrySet().forEach(System.out::println);


        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.newBufferedReader(Paths.get(fileName), Charset.forName("windows-1251")).lines()) {
            AtomicBoolean fl = new AtomicBoolean(false);
            AtomicReference<ParsedContract> parsedContact = new AtomicReference<>(new ParsedContract());
            stream.forEach(line->{

                if (line.isEmpty()){
                    if (!parsedContact.get().isNull()){
                        System.out.println("--------------------------------------------");
                        System.out.println(parsedContact);
                        for (int i = 0; i < parsedContact.get().getInvoiceList().size(); i++){
                            parsedContact.get().setNum1(parsedContact.get().getInvoiceList().get(i).getNum1());
                            parsedContact.get().setNum2(parsedContact.get().getInvoiceList().get(i).getNum2());
                            parsedContact.get().setNum3(parsedContact.get().getInvoiceList().get(i).getNum3());
                            itemDao.create(parsedContact.get());
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
//                System.out.println(parsedContact);
            });

            System.out.println();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void contractInfo(String s, ParsedContract pc){
        Pattern pattern = Pattern.compile("(\\D+)(?<contract>\\d*)(\\D+)(?<phonenumber>\\d*)");
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            pc.setContractnumber(m.group("contract"));
            pc.setPhonenumber(m.group("phonenumber"));
        }

    }

    public static void packageInfo(String s, ParsedContract pc){
        Pattern pattern = Pattern.compile("(.+\\:)(?<packagename>.+)");
        Matcher m = pattern.matcher(s);

        if (m.find()){
            pc.setPackagename(m.group("packagename"));
        }
    }

    private static Invoice parse(String text){
    
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

//            System.out.println(invoice);

            return invoice;
            
        } else {
        
            return null;
        }
            
        
    
    }
    
    private static String parsingPrepare(String s){
        
        String res = s.replaceAll("\"", "")
                      .replace(":,", ":") 
                      .replace(",,,", ":,,");
        
        //number of ","
        if(res.chars().filter(ch -> ch == ',').count()==1){
           res = res.replace(",", ":");
        }
        
        return res;
        
    }




}

