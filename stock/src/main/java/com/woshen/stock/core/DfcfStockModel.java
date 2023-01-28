package com.woshen.stock.core;

import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/17 14:06
 * @Version: 1.0.0
 * @Description: 股票实时数据
 */
@Data
public class DfcfStockModel {

    private Double f1;// "f1": 0.0,//卖十价

    private Integer f2;// "f2": 0//卖十量

    private Double f3;// "f3": 0.0,//卖九价

    private Integer f4;// "f4": 0,//卖九量

    private Double f5;// "f5": 0.0,//卖八价

    private Integer f6;// "f6": 0,//卖八量

    private Double f7;// "f7": 0.0,//卖七价

    private Integer f8;// "f8": 0,//卖七量

    private Double f9;// "f9": 0.0,//卖六价

    private Integer f10;// "f10": 0, //卖六量

    private Double f11;// "f11": 2.47,//卖五价

    private Integer f12;// "f12": 37492,//买五量

    private Double f13;// "f13": 2.48,//卖四价

    private Integer f14;// "f14": 58683,//卖四量

    private Double f15;// "f15": 2.49,//卖三价

    private Integer f16;// "f16": 20240,//卖三量

    private Double f17;// "f17": 2.5,//卖二价

    private Integer f18;// "f18": 21364,//卖二量

    private Double f19;// "f19": 2.51,//卖一价

    private Integer f20;// "f20": 19803,//卖一量

    private Double f21;// "f21": 0.0,//买十价

    private Integer f22;// "f22": 0,//买十量

    private Double f23;// "f23": 0.0,//买九价

    private Integer f24;// "f24": 0,//买九量

    private Double f25;// "f25": 0.0,//买八价

    private Integer f26;// "f26": 0,//买八量

    private Double f27;// "f27": 0.0,//买七价

    private Integer f28;// "f28": 0,//买七量

    private Double f29;// "f29": 0.0,//买六价

    private Integer f30;// "f30": 0,//买六量

    private Double f31;// "f31": 2.56,//买五价

    private Integer f32;// "f32": 16718,//买五量

    private Double f33;// "f33": 2.55,//买四价

    private Integer f34;// "f34": 22766,//买四量

    private Double f35;// "f35": 2.54,//买三价

    private Integer f36;// "f36": 16125,//买三量

    private Double f37;// "f37": 2.53,//买二价

    private Integer f38;// "f38": 16712,//买二量

    private Double f39;// "f39": 2.52,//买一价

    private Integer f40;// "f40": 27106,//买一量

    private Double f43;//当前价

    private Double f44;//最高价

    private Double f45;//最低价

    private Double f46;//"f46": 2.59,//开盘价

    private Integer f47;//"f47": 3105109,//成交量

    private Double f48;// "f48": 787395265.78, //成交额

    private Integer f49;// "f49": 1276070,//外盘

    private Double f50;// "f50": 1.34,//量比

    private Double f51;// "f51": 2.87,//涨停

    private Double f52;// "f52": 2.35,//跌停

    private Double f55;// "f55": 0.056810671,//振幅

    private String f57;// "f57": "002195",//股票代码

    private String f58;// "f58": "二三四五",//名称

    private Integer f59;// "f59": 2,

    private Double f60;// "f60": 2.61,//昨收

    private Integer f62;// "f62": 3,

    private Double f71;// "f71": 2.54,//均价

    private Integer f78;//  "f78": 0,

    private Double f84;//  "f84": 5724847663.0,//总股本

    private Double f85;//  "f85": 5655568155.0,//流通股

    private Long f86;//  "f86": 1673922867,

    private Double f92;//  "f92": 1.6628037,//净资产

    private Double f104;//  "f104": 708635829.19,

    private Double f105;// "f105": 325232438.13,//净利润

    private Integer f107;//  "f107": 0,

    private Integer f110;// "f110": 0,

    private Integer f111;// "f111": 6,

    private Double f116;// "f116": 14426616110.76,//总市值

    private Double f117;// "f117": 14252031750.6,//流通市值

    private Double f120;// "f120": 24.14,

    private Double f121;//  "f121": 26.63,

    private Double f122;// "f122": 25.37,

    private String f127;// "f127": "互联网服务",//行业

    private String f128;// "f128": "上海板块",//地域

    private Double f135;// "f135": 291557653.0,//主力流入

    private Double f136;// "f136": 321526312.0,//主力流出

    private Double f137;//  "f137": -29968659.0,//主力净流出

    private Double f138;// "f138": 120993360.0,//流入超大单

    private Double f139;// "f139": 118818437.0,//流出超大单

    private Double f140;// "f140": 12728823.0,//超大单净流入

    private Double f141;// "f141": 195452683.0,//大单流入

    private Double f142;// "f142": 238132725.0,//大单流出

    private Double f143;// "f143": -42680042.0,//大单净流入

    private Double f144;// "f144": 280941760.0,//中单流入

    private Double f145;// "f145": 323806288.0,//中单流出

    private Double f146;// "f146": -42864528.0,//中单净流入

    private Double f147;// "f147": 320450688.0,//小单流入

    private Double f148;// "f148": 237081031.0,//小单流出

    private Double f149;// "f149": 83369657.0,//小单净流入

    private Integer f152;// "f152": 2,

    private Integer f161;// "f161": 1829039,

    private Double f162;// "f162": 33.14,//市盈率（动）

    private Double f163;// "f163": 36.16,//市盈率（静态）

    private Double f164;// "f164": 35.78,

    private Double f167;// "f167": 1.51,//市净率

    private Double f168;// "f168": 6.06,//换手

    private Double f169;// "f169": -0.09,//短时间波动

    private Double f170;// "f170": -3.45,//跌幅

    private Double f171;// "f171": 5.36,

    private Double f173;// "f173": 3.46,//ROE

    private Double f174;// "f174": 3.05,

    private Double f175;// "f175": 1.77,

    private Integer f177;// "f177": 1089,

    private Double f183;// "f183": 507224757.91,//总营收

    private Double f184;// "f184": -30.6120437907,

    private Double f185;// "f185": 1.316775032822,

    private Double f186;// "f186": 61.8144546792,//毛利率

    private Double f187;// "f187": 64.1433187174,//净利率

    private Double f188;// "f188": 5.1540120148,//负债率

    private Integer f189;// "f189": 20071212,

    private Double f190;// "f190": 0.584019392446,//每股未分配利润

    private Double f191;// "f191": 22.63,

    private Integer f192;// "f192": 58155,

    private String f198;// "f198": "BK0447",

    private Integer f199;// "f199": 90,

    private Double f250;// "f250": 0.0,

    private Double f251;// "f251": 0.0,

    private Double f252;// "f252": 0.0,

    private Double f253;// "f253": 0.0,

    private Double f254;// "f254": 0.0,

    private Integer f255;// "f255": 0,

    private String f256;// "f256": "",

    private Integer f257;// "f257": 0,

    private String f258;// "f258": "",

    private Integer f259;// "f259": 3,

    private Integer f260;// "f260": 0,

    private Double f261;// "f261": 0.0,

    private String f262;// "f262": "",

    private Integer f263;// "f263": 0,

    private String f264;// "f264": "",

    private Integer f265;// "f265": 0,

    private Double f266;// "f266": 0.0,

    private Double f267;// "f267": 0.0,

    private Double f268;// "f268": 0.0,

    private String f269;// "f269": "",

    private Integer f270;// "f270": 0,

    private String f271;// "f271": "",

    private Integer f272;// "f272": 0,

    private Double f273;// "f273": 0.0,

    private Double f274;// "f274": 0.0,

    private Double f275;// "f275": 0.0,

    private Double f276;// "f276": 0.0,

    private Double f277;// "f277": 5724847663.0,//总股本

    private Double f278;// "f278": 14500000.0,

    private Integer f279;// "f279": 1,

    private Integer f288;// "f288": 0,

    private Double f289;// "f289": 0.0,

    private Double f290;// "f290": 0.0,

    private Integer f294;// "f294": 0,

    private Integer f295;//  "f295": 0,



}
