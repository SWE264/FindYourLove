package com.netease.nim.uikit.common.ui.imageview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.nos.NosService;
import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.nos.util.NosThumbImageUtil;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.netease.nimlib.sdk.superteam.SuperTeam;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by huangjun on 2015/11/13.
 */
public class HeadImageView extends CircleImageView {

    public static final int DEFAULT_AVATAR_THUMB_SIZE = (int) NimUIKit.getContext().getResources()
                                                                      .getDimension(
                                                                              R.dimen.avatar_max_size);

    public static final int DEFAULT_AVATAR_NOTIFICATION_ICON_SIZE = (int) NimUIKit.getContext()
                                                                                  .getResources()
                                                                                  .getDimension(
                                                                                          R.dimen.avatar_notification_size);

    private static final int DEFAULT_AVATAR_RES_ID = R.drawable.nim_avatar_default;
    //private static final int SECOND_DEFAULT_AVATAR_RES_ID = R.drawable.nim_avatar_second;

    public HeadImageView(Context context) {
        super(context);
    }

    public HeadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    static Connection conn;

    public static void Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connect to database");
        conn = DriverManager.getConnection("jdbc:mysql://findyourlove.crdb40mgvgxt.us-west-2.rds.amazonaws.com:3306/dating","dating","877152223Zzp!");
    }

    /**
     * 加载用户头像（默认大小的缩略图）
     *
     * @param url 头像地址
     */
    public void loadAvatar(final String url) {
        changeUrlBeforeLoad(null, url, DEFAULT_AVATAR_RES_ID, DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * 加载用户头像（默认大小的缩略图）
     *
     * @param url 头像地址
     */
    public void loadAvatar(String roomId, final String url) {
        changeUrlBeforeLoad(roomId, url, DEFAULT_AVATAR_RES_ID, DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * 加载用户头像（默认大小的缩略图）
     *
     * @param account 用户账号
     */


    public void loadBuddyAvatar(String account) {
        if(conn==null) {
            try {
                Connect();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
                final UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
                if(userInfo==null){
                    System.out.println("the invalid userinfo is "+userInfo);
                    changeUrlBeforeLoad(null,userInfo != null ? userInfo.getAvatar() : null, DEFAULT_AVATAR_RES_ID,
                            DEFAULT_AVATAR_THUMB_SIZE);
                }
                else{
                    String accid=userInfo.getAccount();
                    System.out.println("the valid userinfo is "+accid);
                    PreparedStatement preparedStatement= null;
                    preparedStatement = conn.prepareStatement("Select avatar from user where accid = ?");
                    preparedStatement.setInt(1,Integer.parseInt(accid));
                    ResultSet set=preparedStatement.executeQuery();
                    int avatarId=1;
                    System.out.println("the Avatar ID is "+avatarId);
                    while(set.next()){
                        avatarId=set.getInt(1);
                        System.out.println(avatarId);
                    }

                    String url="";
                    switch (avatarId){
                        case 1: url="https://i.pinimg.com/564x/01/2a/9d/012a9d365def5d21c2fedfb2f23c9cd5.jpg";
                            break;
                        case 2: url="https://i.pinimg.com/originals/d2/47/f8/d247f893f64f27c01235620c795e4604.jpg";
                            break;
                        case 3: url="https://miro.medium.com/max/500/0*3t6VIRr0UmJ8h5Li.jpg";
                            break;
                        case 4: url="https://i.etsystatic.com/13041716/r/il/2b03c1/2318479940/il_570xN.2318479940_ks94.jpg";
                            break;
                        case 5: url="https://i.pinimg.com/564x/76/bf/67/76bf6775d942ddd03404adcd6e58fc9c.jpg";
                            break;
                        case 6: url="https://i.pinimg.com/originals/bd/6f/52/bd6f527fc25008d25d44afeaef4e269c.jpg";
                            break;
                        case 7: url="https://i.pinimg.com/originals/b9/f9/fd/b9f9fdbf18a82f20b7c8bdb56e30ad2b.jpg";
                            break;
                        case 8: url="https://i.pinimg.com/originals/18/7a/3a/187a3a1be0d040bad580c442d081e69f.jpg";
                            break;
                        case 9: url="https://i1.wp.com/papers.co/wallpaper/papers.co-ay03-ilya-kuvshinov-pink-girl-illustration-art-36-3840x2400-4k-wallpaper.jpg";
                            break;
                        case 10: url="https://i.pinimg.com/originals/7a/3b/6d/7a3b6daeddbfc50e52937754351e5ff3.jpg";
                            break;

                    }
                    changeUrlBeforeLoad(null,url,
                            DEFAULT_AVATAR_RES_ID, DEFAULT_AVATAR_THUMB_SIZE);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        }
    public void loadBuddyAvatar(int avatarID) {

                String url="";
                switch (avatarID){
                    case 1: url="https://i.pinimg.com/564x/01/2a/9d/012a9d365def5d21c2fedfb2f23c9cd5.jpg";
                        break;
                    case 2: url="https://i.pinimg.com/originals/d2/47/f8/d247f893f64f27c01235620c795e4604.jpg";
                        break;
                    case 3: url="https://miro.medium.com/max/500/0*3t6VIRr0UmJ8h5Li.jpg";
                        break;
                    case 4: url="https://i.etsystatic.com/13041716/r/il/2b03c1/2318479940/il_570xN.2318479940_ks94.jpg";
                        break;
                    case 5: url="https://i.pinimg.com/564x/76/bf/67/76bf6775d942ddd03404adcd6e58fc9c.jpg";
                        break;
                    case 6: url="https://i.pinimg.com/originals/bd/6f/52/bd6f527fc25008d25d44afeaef4e269c.jpg";
                        break;
                    case 7: url="https://i.pinimg.com/originals/b9/f9/fd/b9f9fdbf18a82f20b7c8bdb56e30ad2b.jpg";
                        break;
                    case 8: url="https://i.pinimg.com/originals/18/7a/3a/187a3a1be0d040bad580c442d081e69f.jpg";
                        break;
                    case 9: url="https://i1.wp.com/papers.co/wallpaper/papers.co-ay03-ilya-kuvshinov-pink-girl-illustration-art-36-3840x2400-4k-wallpaper.jpg";
                        break;
                    case 10: url="https://i.pinimg.com/originals/7a/3b/6d/7a3b6daeddbfc50e52937754351e5ff3.jpg";
                        break;

                }
                changeUrlBeforeLoad(null,url,
                        DEFAULT_AVATAR_RES_ID, DEFAULT_AVATAR_THUMB_SIZE);
            }





    /**
     * 加载用户头像（默认大小的缩略图）
     *
     * @param message 消息
     */
    public void loadBuddyAvatar(IMMessage message) {
        String account = message.getFromAccount();
        if (message.getMsgType() == MsgTypeEnum.robot) {
            RobotAttachment attachment = (RobotAttachment) message.getAttachment();
            if (attachment.isRobotSend()) {
                account = attachment.getFromRobotAccount();
            }
        }
        loadBuddyAvatar(account);
    }

    /**
     * 加载群头像（默认大小的缩略图）
     *
     * @param team 群
     */
    public void loadTeamIconByTeam(final Team team) {
        changeUrlBeforeLoad(null, team != null ? team.getIcon() : null, R.drawable.nim_avatar_group,
                            DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * 加载群头像（默认大小的缩略图）
     *
     * @param team 群
     */
    public void loadSuperTeamIconByTeam(final SuperTeam team) {
        changeUrlBeforeLoad(null, team != null ? team.getIcon() : null, R.drawable.nim_avatar_group,
                            DEFAULT_AVATAR_THUMB_SIZE);
    }


    /**
     * 如果图片是上传到云信服务器，并且用户开启了文件安全功能，那么这里可能是短链，需要先换成源链才能下载。
     * 如果没有使用云信存储或没开启文件安全，那么不用这样做
     */
    private void changeUrlBeforeLoad(String roomId, final String url, final int defaultResId,
                                     final int thumbSize) {

            loadImage(url, defaultResId, thumbSize);

    }

    /**
     * ImageLoader异步加载
     */
    private void loadImage(final String url, final int defaultResId, final int thumbSize) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(defaultResId)
                                                            .error(defaultResId).override(thumbSize,
                                                                                          thumbSize);
        Glide.with(getContext().getApplicationContext()).asBitmap().load(url).apply(requestOptions)
             .into(this);
    }

    /**
     * 解决ViewHolder复用问题
     */
    public void resetImageView() {
        setImageBitmap(null);
    }

    /**
     * 生成头像缩略图NOS URL地址（用作ImageLoader缓存的key）
     */
    private static String makeAvatarThumbNosUrl(final String url, final int thumbSize) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(url,
                                                                   NosThumbParam.ThumbType.Crop,
                                                                   thumbSize, thumbSize) : url;
    }

    public static String getAvatarCacheKey(final String url) {
        return makeAvatarThumbNosUrl(url, DEFAULT_AVATAR_THUMB_SIZE);
    }
}
