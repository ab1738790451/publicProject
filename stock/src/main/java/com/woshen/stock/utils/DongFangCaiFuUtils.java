package com.woshen.stock.utils;

import okhttp3.HttpUrl;

import java.util.Random;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/18 16:30
 * @Version: 1.0.0
 * @Description: 描述
 */
public class DongFangCaiFuUtils {

    private DongFangCaiFuUtils(){}

    public static HttpUrl getSocketDetailHttpUrl(String code){
        //http://94.push2.eastmoney.com/api/qt/stock/sse?fields=f58,f734,f107,f57,f43,f59,f169,f170,f152,f177,f111,f46,f60,f44,f45,f47,f260,f48,f261,f279,f277,f278,f288,f19,f17,f531,f15,f13,f11,f20,f18,f16,f14,f12,f39,f37,f35,f33,f31,f40,f38,f36,f34,f32,f211,f212,f213,f214,f215,f210,f209,f208,f207,f206,f161,f49,f171,f50,f86,f84,f85,f168,f108,f116,f167,f164,f162,f163,f92,f71,f117,f292,f51,f52,f191,f192,f262&mpi=1000&invt=2&fltt=1&secid=1.688617&ut=fa5fd1943c7b386f172d6893dbfba10b&wbp2u=|0|0|0|web
       return new HttpUrl.Builder()
                .scheme("https")
                .host(new Random().nextInt(80) +".push2.eastmoney.com")
                .addEncodedPathSegments("api/qt/stock/sse")
                .addEncodedQueryParameter("ut","fa5fd1943c7b386f172d6893dbfba10b")
                .addEncodedQueryParameter("fields","f120,f121,f122,f174,f175,f59,f163,f43,f57,f58,f169,f170,f46,f44,f51,f168,f47,f164,f116,f60,f45,f52,f50,f48,f167,f117,f71,f161,f49,f530,f135,f136,f137,f138,f139,f141,f142,f144,f145,f147,f148,f140,f143,f146,f149,f55,f62,f162,f92,f173,f104,f105,f84,f85,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f107,f111,f86,f177,f78,f110,f262,f263,f264,f267,f268,f255,f256,f257,f258,f127,f199,f128,f198,f259,f260,f261,f171,f277,f278,f279,f288,f152,f250,f251,f252,f253,f254,f269,f270,f271,f272,f273,f274,f275,f276,f265,f266,f289,f290,f294,f295")
                .addEncodedQueryParameter("fltt","2")
                .addEncodedQueryParameter("secid",(code.startsWith("6")?"1.":"0.")+code)
                .addEncodedQueryParameter("wbp2u","|0|0|0|wap")
                .build();
    }

    public static HttpUrl getSocketTimeSharingHttpUrl(String code){
        return new HttpUrl.Builder()
                .scheme("https")
                .host(new Random().nextInt(80) +".push2.eastmoney.com")
                .addEncodedPathSegments("api/qt/stock/trends2/sse")
                .addEncodedQueryParameter("ut","fa5fd1943c7b386f172d6893dbfba10b")
                .addEncodedQueryParameter("fields1","f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f17")
                .addEncodedQueryParameter("fields2","f51,f52,f53,f54,f55,f56,f57,f58")
                .addEncodedQueryParameter("mpi","1000")
                .addEncodedQueryParameter("secid",(code.startsWith("6")?"1.":"0.")+code)
                .addEncodedQueryParameter("ndays","1")
                .addEncodedQueryParameter("iscr","0")
                .addEncodedQueryParameter("iscca","0")
                .addEncodedQueryParameter("wbp2u","|0|0|0|wap")
                .build();
    }

    public static HttpUrl getSocketZixuanHttpUrl(String secids){
        return new HttpUrl.Builder()
                .scheme("https")
                .host(new Random().nextInt(80) +".push2.eastmoney.com")
                .addEncodedPathSegments("api/qt/ulist/sse")
                .addEncodedQueryParameter("invt","3")
                .addEncodedQueryParameter("pi","0")
                .addEncodedQueryParameter("pz","27")
                .addEncodedQueryParameter("mpi","2000")
                .addEncodedQueryParameter("secids",secids)
                .addEncodedQueryParameter("po","1")
                .addEncodedQueryParameter("ut","6d2ffaa6a585d612eda28417681d58fb")
                .addEncodedQueryParameter("fields","f12,f13,f19,f14,f139,f148,f2,f4,f1,f125,f18,f3,f152,f5,f30,f31,f32,f6,f8,f7,f10,f22,f9,f112,f100")
                .build();
    }
}