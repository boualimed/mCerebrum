package org.md2k.mcerebrum.menu;
/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
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

import android.content.Context;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.md2k.mcerebrum.data.userinfo.UserInfo;

abstract public class Menu {
    public static final int OP_JOIN = 0;
    public static final int OP_ABOUT_STUDY = 1;
    public static final int OP_LOGIN = 2;
    public static final int OP_LOGOUT = 3;
    public static final int OP_LEAVE = 4;
    public static final int OP_HOME = 5;
    public static final int OP_SETTINGS = 6;
    //    public static final int OP_REPORT = 7;
//    public static final int OP_PLOT = 8;
//    public static final int OP_EXPORT_DATA = 9;
    public static final int OP_HELP = 10;


    abstract IProfile[] getHeaderContent(final Context context, UserInfo userInfo, final ResponseCallBack responseCallBack);

    public static IProfile[] getHeaderContent(final Context context, final ResponseCallBack responseCallBack) {
        UserInfo user = UserInfo.getUser(context);
        switch (user.getType(context)) {
            case UserInfo.TYPE_FREEBIE:
                return new Freebie().getHeaderContent(context, user, responseCallBack);
            case UserInfo.TYPE_DOWNLOAD:
                return new Download().getHeaderContent(context, user, responseCallBack);
            case UserInfo.TYPE_SERVER:
                return new Server().getHeaderContent(context, user, responseCallBack);
            default:
                return new Freebie().getHeaderContent(context, user, responseCallBack);
        }
    }

    public static IDrawerItem[] getMenuContent(final Context context, final ResponseCallBack responseCallBack) {
        UserInfo user = UserInfo.getUser(context);
        switch (user.getType(context)) {
            case UserInfo.TYPE_FREEBIE:
                return new Freebie().getMenuContent(responseCallBack);
            case UserInfo.TYPE_DOWNLOAD:
                return new Download().getMenuContent(responseCallBack);
            case UserInfo.TYPE_SERVER:
                return new Server().getMenuContent(responseCallBack);
            default:
                return new Freebie().getMenuContent(responseCallBack);
        }
    }

    static IDrawerItem[] getMenuContent(MenuContent[] menuContent, final ResponseCallBack responseCallBack) {
        IDrawerItem[] iDrawerItems = new IDrawerItem[menuContent.length];
        for (int i = 0; i < menuContent.length; i++) {
            switch (menuContent[i].type) {
                case MenuContent.PRIMARY_DRAWER_ITEM:
                    iDrawerItems[i] = new PrimaryDrawerItem().withName(menuContent[i].name).withIcon(menuContent[i].icon).withIdentifier(menuContent[i].identifier).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            responseCallBack.onResponse((int) drawerItem.getIdentifier());
                            return false;
                        }
                    });
                    break;
                case MenuContent.SECONDARY_DRAWER_ITEM:
                    iDrawerItems[i] = new SecondaryDrawerItem().withName(menuContent[i].name).withIcon(menuContent[i].icon).withIdentifier(menuContent[i].identifier).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            responseCallBack.onResponse((int) drawerItem.getIdentifier());
                            return false;
                        }
                    });
                    break;
                case MenuContent.SECTION_DRAWER_ITEM:
                    iDrawerItems[i] = new SectionDrawerItem().withName(menuContent[i].name).withIdentifier(menuContent[i].identifier).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            responseCallBack.onResponse((int) drawerItem.getIdentifier());
                            return false;
                        }
                    });
                    break;
            }
        }
        return iDrawerItems;
    }
}

class MenuContent {
    static final String PRIMARY_DRAWER_ITEM = "PRIMARY_DRAWER_ITEM";
    static final String SECTION_DRAWER_ITEM = "SECTION_DRAWER_ITEM";
    static final String SECONDARY_DRAWER_ITEM = "SECONDARY_DRAWER_ITEM";
    String name;
    FontAwesome.Icon icon;
    String type;
    long identifier;

    MenuContent(String name, FontAwesome.Icon icon, String type, long identifier) {
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.identifier = identifier;
    }
}