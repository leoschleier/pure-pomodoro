package com.github.leoschleier.purepomodoro.di.module;

import android.app.Application;
import android.content.Context;
import com.github.leoschleier.purepomodoro.data.AppDataManager;
import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.data.db.AppDbHelper;
import com.github.leoschleier.purepomodoro.data.db.DbHelper;
import com.github.leoschleier.purepomodoro.data.prefs.AppPreferencesHelper;
import com.github.leoschleier.purepomodoro.data.prefs.PreferencesHelper;
import com.github.leoschleier.purepomodoro.di.ApplicationContext;
import com.github.leoschleier.purepomodoro.di.DatabaseInfo;
import com.github.leoschleier.purepomodoro.di.PreferenceInfo;
import com.github.leoschleier.purepomodoro.utils.AppConstants;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName(){
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName(){
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager){
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDBHelper(AppDbHelper appDbHelper){
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper){
        return appPreferencesHelper;
    }
}
