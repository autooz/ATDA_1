package mobi.tet_a_tet.atda.tet_a_tet.dates;


/**
 * Created by oleg on 08.07.15.
 */

public class TetGlobalData {
    public static final String TAG = "ATDA";
    public static final String VERSION = "1";
    public static final String TAG_TET_A_TET = "TET A TET APP";
    public static final String EULA_APP_KEY = "EULA_APP"; //EULA key to ask value
    public static final String CHESK_UPDATE = "CHA";
    public static final String TOKEN_SEPARATOR = "*|*"; ///Separator for command/ Fj Example "REQ*|*DSET*|*0677777777*|*99*|*9999*|*mypasswd*|*"
    public static final String POLYGONE_PEAK_first_char = "(";///Separator begin of (latitude,longitude in string (latitude,longitude)
    public static final String POLYGONE_PEAK_SEPARATOR = ")"; ///Separator end of latitude,longitude) in string (latitude,longitude)
    public static final String COORDINATE_SEPARATOR = ",";
    public static final String TOKEN_SEPARATOR_VARS = ":"; ///Tocken separator name:value
    public static final String OUT_OF_CITY = "out_of_city"; /// Marker for out of city position
    public static final String FIRST_REG_OK = "FR_OK"; ///Responce First Registration OK
    public static final String ERROR = "ERROR";
    public static final String REQUEST = "REQ";
    public static final String RESPONCE = "RES";
    public static final String DS_SETUP = "DSET"; ///Ask server to get new setings.
    public static final String DS_SETUP_OK = "DSET_OK" ; ///Responce about setings ia in actual state
    public static final String DS_SETUP_FALSE = "DSET_FALSE" ; ///Responce about setings ia in actual state
    public static final String REQUEST_START_WORK = "DSW"; ///Ask server to start or restart driver shift.
    public static final String REQUEST_FINISH_WORK = "DFW"; ///Ask server to finish driver shift/
    public static final String REQUEST_ORDER_FROM_BORDER = "OFB"; ///Make order from berder
    public static final String REQUEST_NEW_STOP_OR_ZONE = "DNS"; ///Ask service to change zone
    public static final String REQUEST_NEW_STOP_OR_ZONE_BY_HAND = "DNSBH"; ///Ask service to change zone with driver's hand
    public static final String REQUEST_CHANGE_DRVSTATE = "CDS"; ///Ask service to change zone
    public static final String REQUEST_CLOSE_ORDER = "DCO"; ///Close order as completed
    public static final String REQUEST_STOP_LIST = "RSL";
    public static final String OW_STOPLIST ="OW_STOPLIST"; ///List of stops or zone fo chois by driver
    public static final String OW_TAXIMETRE ="OW_TAXIMETRE"; ///Start taximetr
    public static final String CLW_TAXIMETR = "CLW_TAXIMETR"; ///Close window taximentr
    public static final String OW_ONSTOP = "OW_ONSTOP"; ///Command for Open Window driver on stop
    public static final String UP_ONSTOP ="UP_ONSTOP" ; ///Command for Update Window driver on stop
    public static final String UP_DRVSTATE ="UP_DRVSTATE" ; ///Command for Update driver state (free/busy/act.)
    public static final String OW_APP_ACTUAL = "OW_APP_ACTUAL" ;
    public static final String OW_UPDATE_APP = "OW_UPDATE_APP" ;
    public static final String CLOSEPROG = "CLOSEPROG"; ///Command for Close program and stop all services.
    public static final boolean jabberdReconnectAllow = true; ///Allow automatic reconnect to jabberd server

    public static String TAG_TET_A_TET_KEY = "ADT-Tet-A-Tet"; /// Spacial TAG for Tet-A-Tet Assistance
    public static final String TAG_REV_VALUE = "1"; ///Revision on Tet-A-Tet Assistance
    public static final String TAG_EULA_KEY_AGREEMENT = "EULA_TET"; ///Tet-A-Tet EULA key to get value
    /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  ACTIVITY MENAGMENT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
    public static final int JAB_LOGIN_ACTIVITY = 1; ///=JabberdLoginActivity
    public static final int DS_LOGIN_ACTIVITY = 2;
    public static final int GPS_IS_NOT_GOOD = 3;  ///I think this option is not needed
    public static final int WAIT_JB_RESPONCE = 4;  ///I think this option is not needed
    public static String CARRENT_CLASS = ""; ///Indicator about what current activity switched an present time
    public static int CARRENT_ACTIVITY; ///Indicator about what current activity switched an present tim
    public static final int BACKSTACK = 2; ///= OhBackButton()
    /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   Shared servers preference !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
    ///JABBERD CONNECT
    public static String SJBS_KEY_BOOL = "SBS";///SharedPref flag to known was user to jabberd connected or not
    public static final String SJBP_KEY = "SJBP_KEY"; ///SharedPref key to get Jabber UserPassword value
    public static String JBP; /// Jabber User Password value
    public static final String SJBU_KEY = "SJBU_KEY"; ///SharedPref key to get Jabber UserNane value
    public static String JBU; ///Jabber UserName value
    public static final String SJBS_KEY = "SJBS_KEY"; ///SharedPref key to get Jabber Server value
    public static String JBS; ///Jabber Server value
    public static final String SJPORT_KEY = "SJPORT_KEY"; ///SharedPref key to get Jabber Server port value
    public static final int JPORT = 5222; //Jaaber server default port
    public static final String SJCALEE_KEY = "SJCALEE_KEY"; ///Key to ask jabber destination
    ///DS CONNECT
    public static String ADSS_KEY_BOOL = "SDS"; ///SharedPref flag to known was user to Dispatcher server connected or not
    public static String DRVPHONE;
    public static final String SDRVPHONE_KEY = "SDRVPHONE_KEY";///SharedPref key to get DS driver's Sign
    public static final String SDRVSIGN_KEY = "SDRVSIGN_KEY";///SharedPref key to get DS driver's Sign
    public static String DRVSIGN; ///driver's Sign value
    public static final String SCARGOSNUM_KEY = "SCARGOSNUM_KEY"; ///SharedPref key to get car gov. or police registration number
    public static String CARGOSNUM;//car gov. or police registration number value
    public static final String SDRVPASSWD_KEY = "SDRVPASSWD_KEY"; ///SharedPref key to get DS driver's Paasword
    public static String DRVPASSWORD; ///DS driver's Paasword value
    public static String JBC; ///JAbber Callee or host (for example ="google.talk")
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Dynamic chanhed Date !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static boolean jabConnectedAndRuning = false; ///Flag about cjnnection of Jabberd connection
    public static boolean JconnectedANDloged; ///Flag to known is Jabberd Connecten or not at this time
    public static boolean DSconnectedANDloged; ///Flag to known is APP Connected with Dispatcher Server or not at this time
    public static int jabLoginAttempts = 0; ///Number of user's Attempts to login Jabberd Server/
    public static int DsLoginAttempts = 0; ///Number of user's Attempts to login Dispatcher or Assistant Server
    public static boolean notReply; ///Not need to reply for massage
    public static int JabServiceName = 101; ///I think this option is not needed
    public static String out_of_city_key = "out_of_city"; ///Marker  to now when driver out of all zones



    public void setGlobalData(String name, String newVar) {
        name = newVar;
    }

}
