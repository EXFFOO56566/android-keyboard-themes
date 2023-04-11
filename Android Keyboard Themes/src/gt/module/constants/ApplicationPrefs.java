package gt.module.constants;

import gt.module.bean.Bean;
import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPrefs {

    private static ApplicationPrefs prefs;

    protected Context context;
    public static final String PREF_NAME = "keybord";

    private ApplicationPrefs(Context context) {
        this.context = context;
    }

    public static ApplicationPrefs getInstance(Context context) {

        if (prefs == null) {
            prefs = new ApplicationPrefs(context);
        }
        return prefs;
    }
    
    private static final String THEMES_ID = "themesid";
    private static final String BITMAP = "bitmap";
  
    /*Addition level */
    public void setThemesId(int addLevel) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(THEMES_ID,addLevel);
        editor.commit();
    }
    public int getThemesId() {
        return getPrefs().getInt(THEMES_ID, 0);
    }
 
    private static final String CUSTOME_THEMES_BACKGROUND = "customethemesbackground";
    private static final String CUSTOME_THEMES_KEY_BACKGROUND = "customethemeskaybackground";
    private static final String CUSTOME_THEMES_BACKGROUND_FROME_GALLERY = "customethemesbackgroundfromegallery";
    private static final String CUSTOME_THEMES_KEY_FONT_COLOR = "customethemeskeyfontcolor";
    
    /*select themes background*/
    public void setCustomeThemesBackground(int themesresid) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(CUSTOME_THEMES_BACKGROUND,themesresid);
        editor.commit();
    }
    public int getCustomeThemesBackground() {
        return getPrefs().getInt(CUSTOME_THEMES_BACKGROUND, 0);
    }
    
    /*select themes key background*/
    public void setCustomeThemesKeyBackground(int themesresid) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(CUSTOME_THEMES_KEY_BACKGROUND,themesresid);
        editor.commit();
    }
    public int getCustomeThemesKeyBackground() {
        return getPrefs().getInt(CUSTOME_THEMES_KEY_BACKGROUND, 0);
    }
    
    /*select themes background frome gallery*/
    public void setCustomeThemesBackgroundGallery(int themesresid) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(CUSTOME_THEMES_BACKGROUND_FROME_GALLERY,themesresid);
        editor.commit();
    }
    public int getCustomeThemesBackgroundGallery() {
        return getPrefs().getInt(CUSTOME_THEMES_BACKGROUND_FROME_GALLERY, 0);
    }
    
    /*select themes background frome gallery*/
    public void setCustomeThemesFontColor(int themesresid) {
        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(CUSTOME_THEMES_KEY_FONT_COLOR,themesresid);
        editor.commit();
    }
    public int getCustomeThemesFontColor() {
        return getPrefs().getInt(CUSTOME_THEMES_KEY_FONT_COLOR, 0);
    }
    
	protected SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor getPrefsEditor() {
        return getPrefs().edit();
    }

}
