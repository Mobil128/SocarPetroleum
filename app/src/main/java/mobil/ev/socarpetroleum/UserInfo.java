package mobil.ev.socarpetroleum;

public class UserInfo {
    public UserInfo() {
    }



    public static String sekil;
    public static String user_tel;
    public static String user_id;
    public static String user_name;
    public static String user_vez;
    public static String user_sifre;
    public static String user_key;
    public static String user_ydm;
    public static String user_email;
    public static String cenlerin_sayi;
    public static String yanacaq_sayi;

    public static String[] getYan_novu() {
        return yan_novu;
    }

    public static String[] getYan_novu_sayi() {
        return yan_novu_sayi;
    }

    public static String[] yan_novu;

    public static void setYan_novu_sayi(String[] yan_novu_sayi) {
        UserInfo.yan_novu_sayi = yan_novu_sayi;
    }

    public static void setYan_novu(String[] yan_novu) {
        UserInfo.yan_novu = yan_novu;
    }

    public static String[] yan_novu_sayi ;
    public static String getCenlerin_sayi() {
        return cenlerin_sayi;
    }

    public static String getYanacaq_sayi() {
        return yanacaq_sayi;
    }

    public static void setCenlerin_sayi(String cenlerin_sayi) {
        UserInfo.cenlerin_sayi = cenlerin_sayi;
    }

    public static void setYanacaq_sayi(String yanacaq_sayi) {
        UserInfo.yanacaq_sayi = yanacaq_sayi;
    }






    public static String getUser_Sekil() {
        return sekil;
    }

    public static void setUser_Sekil(String sekil) {
        UserInfo.sekil = sekil;
    }
    public static void setUser_email(String user_email) {
        UserInfo.user_email = user_email;
    }



    public static String getUser_email() {
        return user_email;
    }



    public static String getUser_tel() {
        return user_tel;
    }

    public static  void setUser_tel(String user_tel) {
        UserInfo.user_tel = user_tel;
    }

    public static  String getUser_id() {
        return user_id;
    }

    public static  void setUser_id(String user_id) {
        UserInfo.user_id = user_id;
    }

    public static  String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
       UserInfo.user_name = user_name;
    }

    public static  String getUser_vez() {
        return user_vez;
    }

    public static  void setUser_vez(String user_vez) {
        UserInfo.user_vez = user_vez;
    }

    public static String getUser_sifre() {
        return user_sifre;
    }

    public static void setUser_sifre(String user_sifre) {
        UserInfo.user_sifre = user_sifre;
    }

    public static  String getUser_key() {
        return user_key;
    }

    public static void setUser_key(String user_key) {
      UserInfo.user_key = user_key;
    }

    public static String getUser_ydm() {
        return user_ydm;
    }

    public static void setUser_ydm(String user_ydm) {
        UserInfo.user_ydm = user_ydm;
    }
}
