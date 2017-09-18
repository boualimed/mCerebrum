package org.md2k.mcerebrum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.md2k.mcerebrum.app.ApplicationManager;
import org.md2k.mcerebrum.configuration.ConfigManager;
import org.md2k.mcerebrum.study.StudyInfoController;
import org.md2k.mcerebrum.user.UserInfoController;
import org.md2k.md2k.system.study.StudyInfo;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source status must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class BootReceiver extends BroadcastReceiver
{
    ConfigManager configManager;
    UserInfoController userInfoController;
    StudyInfoController studyInfoController;
    ApplicationManager applicationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        configManager=new ConfigManager();
        userInfoController=new UserInfoController();
        studyInfoController = new StudyInfoController();
        applicationManager = new ApplicationManager();
        if(readConfig() && studyInfoController.isStarted() && studyInfoController.isStartAtBoot()){
/*
//TODO: check
                AppInfoE appInfo = applicationManager.getStudy();
            if(appInfo !=null) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
//            intent.putExtra(UserInfo.class.getSimpleName(), userInfo);
//            intent.putExtra(StudyInfo.class.getSimpleName(), studyInfo);
            ..intent.putExtra("AppInfo",(applicationManager.getAppInfo()));
                startActivity(intent);
                finish();
            }
*/

        }
    }
    public boolean readConfig(){
        if(configManager.read() && configManager.isConfigured()) {
            userInfoController.set();
            studyInfoController.set(configManager.getConfig());
            applicationManager.set(configManager.getConfig().getApplications());
            if (studyInfoController.getType().toUpperCase().equals(StudyInfo.FREEBIE))
                userInfoController.setTitle("Default");
            return true;
        }else{
            configManager.clear();
            userInfoController.clear();
            studyInfoController.clear();
            applicationManager.stop();
            return false;
        }
    }

}
