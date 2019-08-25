package com.katana.wx.material.conn;

import com.alibaba.fastjson.JSONObject;
import com.katana.wx.common.conn.Connection;
import com.katana.wx.common.entity.results.ResultState;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.utils.ConvertJsonUtils;
import com.katana.wx.material.entity.BasicItem;
import com.katana.wx.material.entity.NewsEntity;
import com.katana.wx.material.request.BatchGetRequest;
import com.katana.wx.material.request.DeleteMaterialRequest;
import com.katana.wx.material.request.GetMaterial;
import com.katana.wx.material.request.UpdateNews;
import com.katana.wx.material.response.GetMaterialCount;
import com.katana.wx.material.response.GetNewsResponse;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by mumu on 2017/12/12.
 */
public class MaterialConnection extends Connection {

    //新增永久图文素材
    private static String ADD_NEW = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    //上传图文消息内的图片获取URL
    private static String ADD_IMAGE = "https://api.weixin.qq.com/cgi-bin/material/uploadimg";
    //新增其他类型永久素材
    private static String ADD_OTHER_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material";
    //获取永久素材
    private static String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    //删除永久素材
    private static String DEL_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material";
    //修改永久图文素材
    private static String UPDATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/update_news";
    //获取素材总数
    private static String GET_MATERIAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
    //获取素材列表
    private static String BATCH_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";

    /**
     * 新增永久图文素材
     *
     * @param articles
     * @param token    授权token
     * @return {"media_id":MEDIA_ID}
     */
    public static WechatResult addArticles(List<NewsEntity> articles, String token) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        TreeMap<String, List<NewsEntity>> postParams = new TreeMap<String, List<NewsEntity>>();
        postParams.put("articles", articles);
        String data = JSONObject.toJSONString(postParams);
        String result = HttpsDefaultExecute("POST", ADD_NEW, map, data);
        WechatResult wechatResult = ConvertJsonUtils.ConvertJavaObjectByKeyword(result, BasicItem.class, "media_id");

        return wechatResult;
    }


    /**
     * 添加其他永久素材
     *
     * @param accessToken
     * @param mediaPath
     * @return
     */
    public static String addOtherMaterial(String accessToken, String mediaPath) {
        String result = "";
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        result = defaultUploadImg(ADD_OTHER_MATERIAL, params, "POST", mediaPath, "");
        return result;
    }


    /**
     * 获取图文消息素材
     *
     * @param getMaterial
     * @param token
     * @return
     */
    public static WechatResult getNews(GetMaterial getMaterial, String token) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        String jsonData = JSONObject.toJSON(getMaterial).toString();
        String result = HttpsDefaultExecute("POST", GET_MATERIAL, map, jsonData);

        WechatResult wechatResult = ConvertJsonUtils.ConvertJavaObjectByKeyword(result, GetNewsResponse.class, "news_item");
        return wechatResult;
    }

    /**
     * 根据media_id删除素材
     *
     * @param request
     * @param token
     * @return
     */
    public static boolean deleteNews(DeleteMaterialRequest request, String token) {
        boolean fig = true;
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        String jsonData = JSONObject.toJSON(request).toString();
        String result = HttpsDefaultExecute("GET", DEL_MATERIAL, map, jsonData);
        JSONObject object = JSONObject.parseObject(result);
        if (object.getInteger("errcode") != 0 || object.getString("errmsg") != "ok") { // 删除失败
            fig = false;
        }
        return fig;
    }

    /**
     * 更新图文素材
     *
     * @param updateNews
     * @param token
     * @return
     */
    public static ResultState updateNews(UpdateNews updateNews, String token) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        String jsonData = JSONObject.toJSON(updateNews).toString();
        String result = HttpsDefaultExecute("POST", UPDATE_NEWS, map, jsonData);

        return ConvertResultState(result);
    }


    /**
     * 获取素材的数量
     *
     * @param token
     * @return
     */
    public static WechatResult getMaterialCount(String token) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", token);
        String result = HttpsDefaultExecute("GET", GET_MATERIAL_COUNT, map, "");

        WechatResult wechatResult = ConvertJsonUtils.ConvertJavaObjectByKeyword(result, GetMaterialCount.class, "voice_count");
        return wechatResult;
    }

    /**
     * 获取永久素材列表
     *
     * @param accessToken
     * @param request
     * @return
     */
    public static WechatResult getMaterList(String accessToken, BatchGetRequest request) {
        WechatResult resultObj = null;
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        //post params
        String data = JSONObject.toJSONString(request);
        String result = HttpsDefaultExecute("POST", BATCH_GET_MATERIAL, params, data);
        resultObj = ConvertMaterList(result, request.getType());        //注意此处的type应该与参数中的type保持一致
        return resultObj;
    }


}
