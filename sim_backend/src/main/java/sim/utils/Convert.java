/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.utils;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_UP;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

/**
 *
 * @author dit78local
 */
public class Convert
{
//    private static String StringData;
//    private static Integer intData;
//    private static LocalDate date;
    //private static final Logger logger = LogManager.getLogger(Convert.class);
    //**************************************************************************
    /**
     * convert long - to string
     *
     * @param longData
     * @return StringData
     */
    public static String convLongToString(long longData)
    {
//        StringData = Long.toString(longData);
//        return StringData;
        return Long.toString(longData);
    }
    //**************************************************************************
    /**
     * convert long - int
     *
     * @param longData
     * @return intData
     */
    public static Integer convLongToInt(long longData)
    {
//        intData = (int) (long) longData;
//        return intData;
        return (int) (long) longData;
    }
    //**************************************************************************
    /**
     * convert int - string
     *
     * @param intData
     * @return StringData
     */
    public static String convIntToString(int intData)
    {
//        StringData = Integer.toString(intData);
//        return StringData;
        return Integer.toString(intData);
    }
    //**************************************************************************
    /**
     * convert string - int
     *
     * @param stringData
     * @return intData
     */
    public static Integer convStringToInt(String stringData)
    {
//        intData = Integer.parseInt(StringData);
//        return intData;
        return Integer.parseInt(stringData);
    }
    //**************************************************************************
    /**
     * covert string to LocalDate
     *
     * @param stringDate
     * @return date
     */
    public static LocalDate convStringToLocalD(String stringDate)
    {
//        String pattern = "yyyy-MM-dd";
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//        date = LocalDate.parse(StringDate, dateFormatter);
//        return date;
        return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    //**************************************************************************
    /**
     * convert String - BigDecimal
     *
     * @param sd
     * @return BigDecimal
     */
    public static BigDecimal convStringToBigDecimal(String sd)
    {
        return new BigDecimal(sd).setScale(2, ROUND_HALF_UP);
    }
    //**************************************************************************
    /**
     * convert String - BigDecimal
     * @param sd
     * @param scale
     * @return 
     */
    public static BigDecimal convStringToBigDecimal(String sd, int scale)
    {
        return new BigDecimal(sd).setScale(scale, ROUND_HALF_UP);
    }
    //**************************************************************************
    /**
     * Округление (обрезание) для вывода
     *
     * @param value - значение
     * @param scale - количество знаков после запятой
     * @return String
     */
    public static String roundingToString(double value, int scale)
    {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).toString();
    }
    //**************************************************************************
    /**
     * Convert SQL date to LocalDate
     *
     * @param date
     * @return LocalDate to put in DatePicker
     */
    public static LocalDate convSQLdateToLocalDate(Date date)
    {
//        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        return ld;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    /**
     * Convert sqldate to string
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String convSQLdateToString(Date date, String formatPattern)
    {
        return new SimpleDateFormat(formatPattern).format(date);
        //return date.toInstant().atZone(ZoneId.systemDefault()).toString();
    }
    //**************************************************************************
    /**
     * Convert date to string
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String convDateToString(Date date, String formatPattern)
    {
        return new SimpleDateFormat(formatPattern).format(date);
        //return date.toInstant().atZone(ZoneId.systemDefault()).toString();
    }
    //**************************************************************************
    /**
     * Convert Date to LocalDate
     *
     * @param date
     * @return LocalDate to put in DatePicker
     */
    public static LocalDate convDateToLocalDate(Date date)
    {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    //**************************************************************************
    /**
     * Convert LocalDate to Date
     *
     * @param ld
     * @return Date
     */
    public static Date convLocalDateToDate(LocalDate value)
    {
        return Date.from(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    //**************************************************************************
    /**
     * Convert LocalDate to SQL date
     *
     * @param ld
     * @return SQL date
     */
    public static Date convLocalDateToSQLdate(LocalDate ld)
    {
//        java.sql.Date dateSql = java.sql.Date.valueOf(ld);
//        return dateSql;
        return java.sql.Date.valueOf(ld);
    }

//------------------------------------------------------------------------------
    /**
     * Convert String date to SQL date
     *
     * @param stringDate
     * @return SQL date
     */
    public static Date convStringToSQLdate(String stringDate)
    {
//        String pattern = "yyyy-MM-dd";
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//        date = LocalDate.parse(StringDate, dateFormatter);
//        java.sql.Date dateSql = java.sql.Date.valueOf(date);
//        return dateSql;
        return java.sql.Date.valueOf(LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
//------------------------------------------------------------------------------
//// convert int to localdatatime (second = 00)
//toDate = LocalDateTime.of(YYYY, MM, DD, hour, min, 00);
    /**
     *
     * @param <T> - тип возвращаемого значения
     * @param text - текстовое значение поля
     * @param clas - реальный тип поля
     * @return - значение поля реального типа
     * Метод работает только с предопределёнными шаблонами даты и времени!!!!
     * Форматоры и DynField, работают тоже с этими шаблонами
     */
    public static <T> T textToType(String text, Class clas)
    {
        final String DATE_PATTERN = "dd.MM.yyyy";
        final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
        final String TIME_PATTERN = "HH:mm:ss";
        final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE_PATTERN);
        final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME_PATTERN);
        T value = null;
        if (text.equals(""))
        {
            return value;
        }
        if (clas == Byte.class || clas == byte.class)
        {
            value = (T) Byte.valueOf(text);
        }
        else if (clas == Short.class || clas == short.class)
        {
            value = (T) Short.valueOf(text);
        }
        else if (clas == Integer.class || clas == int.class)
        {
            value = (T) Integer.valueOf(text);
        }
        else if (clas == Long.class || clas == long.class)
        {
            value = (T) Long.valueOf(text);
        }
        else if (clas == BigInteger.class)
        {
            value = (T) new BigInteger(text);
        }
        else if (clas == Float.class || clas == float.class)
        {
            value = (T) Float.valueOf(text);
        }
        else if (clas == Double.class || clas == double.class)
        {
            value = (T) Double.valueOf(text);
        }
        else if (clas == BigDecimal.class)
        {
            value = (T) new BigDecimal(text);
        }
        else if (clas == Boolean.class || clas == boolean.class)
        {
            value = (T) Boolean.valueOf(text);
        }
        else if (clas == LocalDateTime.class)
        {
            value = (T) LocalDateTime.parse(text, formatterDateTime);
        }
        else if (clas == LocalDate.class)
        {
            value = (T) LocalDate.parse(text, formatterDate);
        }
        else if (clas == LocalTime.class)
        {
            value = (T) LocalTime.parse(text, formatterTime);
        }
        else if (clas == Timestamp.class)
        {
            try
            {
                Date dt = (Date) new SimpleDateFormat(DATE_TIME_PATTERN).parse(text);
                value = (T) (Timestamp) new java.sql.Timestamp(dt.getTime());
            }
            catch (ParseException ex)
            {
                //Logger.getLogger(DynField.class.getName()).log(Level.SEVERE, null, ex);
                //logger.error("Ошибка при преобразовании текста в тип " + text);
            }
        }
        else if (clas == Date.class)
        {
            try
            {
                Date dt;
                switch (text.length())
                {
                    case 8:
                        dt = (Date) new SimpleDateFormat(TIME_PATTERN).parse(text);
                        value = (T) (Time) new java.sql.Time(dt.getTime());
                        break;
                    case 10:
                        dt = (Date) new SimpleDateFormat(DATE_PATTERN).parse(text);
                        value = (T) dt;
                        break;
                    default:
                        dt = (Date) new SimpleDateFormat(DATE_TIME_PATTERN).parse(text);
                        value = (T) (Timestamp) new java.sql.Timestamp(dt.getTime());
                        break;
                }
            }
            catch (ParseException ex)
            {
                //Logger.getLogger(DynField.class.getName()).log(Level.SEVERE, null, ex);
                //logger.error("Ошибка при преобразовании текста в тип " + text);
            }
        }
        else if (clas == Time.class)
        {
            try
            {
                Date dt = (Date) new SimpleDateFormat(TIME_PATTERN).parse(text);
                value = (T) (Time) new java.sql.Time(dt.getTime());
            }
            catch (ParseException ex)
            {
                //Logger.getLogger(DynField.class.getName()).log(Level.SEVERE, null, ex);
                //logger.error("Ошибка при преобразовании текста в тип " + text);
            }
        }
        else
        {
            value = (T) text;
        }
        return value;
    }
    
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    
    /**
     * 
     * @param Integer period
     * @return String mm.yyyy
     */
    public static String periodToString(Integer period){
        if (period == null)
            return "";
        
        try{
           String tmp = period.toString();
           return tmp.substring(4, 6).concat(".").concat(tmp.substring(0, 4));
            
        }catch(Exception ex){
            return "";
        }
        
    }    

    /**
     * Set the time of the given Date
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param ms
     *
     * @return new instance of java.util.Date with the time set
     */
    public static Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gc.set(Calendar.MINUTE, minute);
        gc.set(Calendar.SECOND, second);
        gc.set(Calendar.MILLISECOND, ms);
        return gc.getTime();
    }

    /**
     * 
     * @param Date date
     * @return 23:59:59 of date 
     */
    public static Date lastMoment(Date date){
        
        GregorianCalendar calendarDate = new GregorianCalendar();
        calendarDate.setTime(date);

        GregorianCalendar calendarDateOut = new GregorianCalendar(calendarDate.get(Calendar.YEAR), 
                                                                  calendarDate.get(Calendar.MONTH) , 
                                                                  calendarDate.get(Calendar.DATE));
        calendarDateOut.add(Calendar.HOUR, 23);
        calendarDateOut.add(Calendar.MINUTE, 59);
        calendarDateOut.add(Calendar.SECOND, 59);
            
        return calendarDateOut.getTime();
    }   
    
        /**
     * LocalDateTime To Timestamp
     *
     * @param now
     * @return
     */
    public static Timestamp convLocalDateTimeToTimestamp(LocalDateTime now) {
        return Timestamp.valueOf(now);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
           Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
           return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    } 
    
    
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        return null;
                    }
                    return list.get(0);
                }
        );
    }
        /** 
     * creatd random string (verification key length 6)
     *  @return Date first day of period
     */
    public static String givenRandomString(Integer stringlength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = stringlength;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }
}
