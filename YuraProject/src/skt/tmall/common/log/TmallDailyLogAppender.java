package skt.tmall.common.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 로그 파일명을 날짜로 이용하여 생성하는 Log Appender
 * 
 * @version 1.0 2011/08/09
 * @author krovere
 */
public class TmallDailyLogAppender extends FileAppender {

    /** 운영체제의 디렉토리 구분문자. */
    public static final String FILE_SEPERATOR = System.getProperty("file.separator");
    
    /**
     * 저장할 디렉토리 명
     */
    private String directory;

    /**
     * 저장할 파일의 접두사명
     */
    private String prefix;

    /**
     * 저장할 파일의 접미사명
     */
    private String suffix;

    /**
     * 파일 백업시 사용할 날짜 패턴
     */
    private String datePattern;

    /**
     * 다음날짜
     */
    private long tomorrow;

    /**
     * 경로명
     */
    private File path;
    
    /**
     * WAS Instance Name
     */
    private String wasInstanceKey;
    private String wasInstanceName;

    /**
     * Calendar instance
     */
    private Calendar calendar;

    /**
     * Constructor
     */
    public TmallDailyLogAppender() {
        datePattern = "YYYYMMDD";
        tomorrow = 0L;
    }

    /**
     * 저장할 디렉토리를 설정한다.
     * 
     * @param directory 디렉토리 경로
     */
    public void setDirectory( String directory ) {
        this.directory = directory;
    }

    /**
     * 저장할 파일의 접두사명을 설정한다.
     *
     * @param prefix
     */
    public void setPrefix( String prefix ) {
        this.prefix = prefix;
    }

    /**
     * 저장할 디렉토리 경로를 반환한다.
     *
     * @return 저장할 디렉토리 경로
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * 저장할 파일의 접두사명을 반환한다.
     *
     * @return 저장할 파일의 접두사명
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 저장할 파일의 접미사명을 반환한다.
     *
     * @return 저장할 파일의 접미사명
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 저장할 파일의 접미사명을 저장한다.
     *
     * @param suffix 저장할 파일의 접미사명
     */
    public void setSuffix( String suffix ) {
        this.suffix = suffix;
    }

    /**
     * 백업파일 생성시 사용할 날짜 패턴을 설정한다.
     *
     * @param pattern 날짜 패턴
     */
    public void setDatePattern( String pattern ) {
        datePattern = pattern;
    }

    /**
     * 백업파일 생성시 사용할 날짜 패턴을 반환한다.
     *
     * @return 날짜 패턴
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * WAS Instance 명을 반환한다.
     * 
     * @return wasInstanceName 값을 반환한다.
     */
    public String getWasInstanceKey() {
        return wasInstanceKey;
    }

    /**
     * WAS Instance 명을 설정한다.
     * 
     * @param 저장할 WAS Instance 명
     */
    public void setWasInstanceKey( String wasInstanceName ) {
        this.wasInstanceKey = wasInstanceName;
    }

    /* (non-Javadoc)
     * @see org.apache.log4j.FileAppender#activateOptions()
     */
    public void activateOptions() {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        if (directory == null || directory.length() == 0)
            directory = ".";
        if (wasInstanceKey == null || wasInstanceKey.length() == 0 ) 
            wasInstanceKey = "";

        wasInstanceName = System.getProperty(wasInstanceKey);
        if (wasInstanceName == null || wasInstanceName.length() == 0 ) 
            wasInstanceName = "";
        
        path = new File(directory+FILE_SEPERATOR+wasInstanceName);
        if (!path.isAbsolute()) {
            String s = System.getProperty("catalina.base");
            if (s != null)
                path = new File(s, directory);
        }

        // log.
        System.out.println( "## TmallLogAppender log path : " + path.toString() );
        path.mkdirs();

        calendar = Calendar.getInstance();
    }

    /* (non-Javadoc)
     * @see org.apache.log4j.WriterAppender#append(org.apache.log4j.spi.LoggingEvent)
     */
    public void append( LoggingEvent loggingevent ) {
        try {
            if (layout == null) {
                errorHandler.error("No layout set for the appender named [" + name + "].");
                return;
            }

            if (this.calendar == null) {
        	errorHandler.error("Improper initialization for the appender named ["+ name+"].");
        	return;
            }

            long l = System.currentTimeMillis();
            if (l >= tomorrow) {
                SimpleDateFormat fmt = new SimpleDateFormat(datePattern);
                String strTime = fmt.format(new java.util.Date(l));

                calendar.setTime(new Date(l));
                m_tomorrow(calendar);
                tomorrow = calendar.getTime().getTime();

                File file = new File(directory+FILE_SEPERATOR+wasInstanceName, prefix + strTime + suffix);
                fileName = file.getAbsolutePath();
                super.activateOptions();
            }
            if (qw == null) {
                errorHandler.error("No output stream or file set for the appender named [" + name + "].");
                return;
            }
            else {
                subAppend(loggingevent);
                return;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void m_tomorrow(Calendar calendar) {
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
	calendar.clear();
	calendar.set(year, month, day);
    }

}

