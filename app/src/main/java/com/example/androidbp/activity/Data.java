package com.example.androidbp.activity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class Data {
    public static ArrayList<Achievement> allAchievement;
    public static ArrayList<Achievement> completedAchievement;
    public static ArrayList<Achievement> saveAchievement;
    public Data(){
        allAchievement = new ArrayList<Achievement>();
        completedAchievement =new ArrayList<Achievement>();
        saveAchievement =new ArrayList<Achievement>();

        addAllAchievement();
        addCompletedAchievement();
        addSaveAchievement();
    }

    public void addAllAchievement(){
        allAchievement.add(new Achievement("Ban Ing Koaw","Saraburi"));
        allAchievement.add(new Achievement("Kasetsart","Bankok"));
        allAchievement.add(new Achievement("Nam Tok Chet Sao Noi","Saraburi"));
        allAchievement.add(new Achievement("Khun Malee Grape Farm","Saraburi"));
        allAchievement.add(new Achievement("Papasara Grape Farm","Saraburi"));
        allAchievement.add(new Achievement("สวนองุ่นภูอมรและไวน์องุ่นภูอมร","Saraburi"));
        allAchievement.add(new Achievement("Kamnan Meng Grape Farm","Saraburi"));
        allAchievement.add(new Achievement("อุโมงค์ต้นไม้","Saraburi"));
        allAchievement.add(new Achievement("ทุ่งทานตะวันแสลงพัน","Saraburi"));
        allAchievement.add(new Achievement("MuakLek ATV","Saraburi"));
        allAchievement.add(new Achievement("Buddhist Temple วัดพระพุทธฉาย ","Saraburi"));
        allAchievement.add(new Achievement("Wat Phra Phutthabat","Saraburi"));
        allAchievement.add(new Achievement("ศูนย์ศึกษาธรรมชาติและท่องเที่ยวเชิงนิเวศน์เจ็ดคต-โป่งก้อนเสา","Saraburi"));
        allAchievement.add(new Achievement("น้ำตกโกรกอีดก","Saraburi"));
        allAchievement.add(new Achievement("Pa Sak Cholasit ","Saraburi"));
        allAchievement.add(new Achievement("Namtok Sam Lan National Park","Saraburi"));
        allAchievement.add(new Achievement("laisakunahansa","Saraburi"));
        allAchievement.add(new Achievement("Rai Kusuma Resort ","Saraburi"));
        allAchievement.add(new Achievement("Wat Tham Phra Phothisat","Saraburi"));
        allAchievement.add(new Achievement("สวนรุกขชาติมวกเหล็ก","Saraburi"));
        allAchievement.add(new Achievement("ร้านอาหารเคียงวาริน อ. เสาไห้","Saraburi"));
        allAchievement.add(new Achievement("Wat Pa Sawan Bun","Saraburi"));

    }

    public void addSaveAchievement(){
        saveAchievement.add(new Achievement("Ban Ing Koaw","Saraburi"));
        saveAchievement.add(new Achievement("Kasetsart","Bankok"));
        saveAchievement.add(new Achievement("Nam Tok Chet Sao Noi","Saraburi"));
        saveAchievement.add(new Achievement("Khun Malee Grape Farm","Saraburi"));
        saveAchievement.add(new Achievement("Rai Kusuma Resort ","Saraburi"));
        saveAchievement.add(new Achievement("Wat Tham Phra Phothisat","Saraburi"));
        saveAchievement.add(new Achievement("สวนรุกขชาติมวกเหล็ก","Saraburi"));
        saveAchievement.add(new Achievement("ร้านอาหารเคียงวาริน อ. เสาไห้","Saraburi"));
        saveAchievement.add(new Achievement("Wat Pa Sawan Bun","Saraburi"));

    }

    public void addCompletedAchievement(){
        completedAchievement.add(new Achievement("Ban Ing Koaw","Saraburi"));
        completedAchievement.add(new Achievement("Kasetsart","Bankok"));
        completedAchievement.add(new Achievement("Nam Tok Chet Sao Noi","Saraburi"));
        completedAchievement.add(new Achievement("Khun Malee Grape Farm","Saraburi"));
        completedAchievement.add(new Achievement("Papasara Grape Farm","Saraburi"));
        completedAchievement.add(new Achievement("สวนองุ่นภูอมรและไวน์องุ่นภูอมร","Saraburi"));
        completedAchievement.add(new Achievement("Kamnan Meng Grape Farm","Saraburi"));
        completedAchievement.add(new Achievement("อุโมงค์ต้นไม้","Saraburi"));
        completedAchievement.add(new Achievement("ทุ่งทานตะวันแสลงพัน","Saraburi"));
        completedAchievement.add(new Achievement("MuakLek ATV","Saraburi"));
        completedAchievement.add(new Achievement("Buddhist Temple วัดพระพุทธฉาย ","Saraburi"));
        completedAchievement.add(new Achievement("Wat Phra Phutthabat","Saraburi"));

    }



}
