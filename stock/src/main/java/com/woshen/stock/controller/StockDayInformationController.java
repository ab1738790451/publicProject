package com.woshen.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.common.webcommon.model.PageInfo;
import com.woshen.stock.constant.MainSubRate;
import com.woshen.stock.constant.PriceChangeType;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.server.IStockService;
import com.woshen.stock.vo.StockDayInformationVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 股票每日数据表 前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Controller
@RequestMapping("stockDayInformation")
public class StockDayInformationController extends AbstractController<Integer, StockDayInformation> {

    @Resource
    private IStockDayInformationService stockDayInformationServiceImpl;

    @Resource
    private IStockService stockServiceImpl;

    @Override
    public IStockDayInformationService getService() {
    return stockDayInformationServiceImpl;
    }

    @Override
    public Map<String, ?> afterList(HttpServletRequest request, HttpServletResponse response, StockDayInformation queryData, Page<StockDayInformation> pageData) {
     List<StockDayInformation> records = pageData.getRecords();
     if(!CollectionUtils.isEmpty(records)){
      List<String> collect = records.stream().map(StockDayInformation::getCode).distinct().collect(Collectors.toList());
      QueryWrapper<Stock> baseWrapper = stockServiceImpl.getBaseWrapper(new Stock());
      baseWrapper.in("code",collect);
      List<Stock> list = stockServiceImpl.list(baseWrapper);
      Map<String, String> map = list.stream().collect(Collectors.toMap(Stock::getCode, Stock::getName));
      records.stream().forEach(t-> {
          t.setName(map.get(t.getCode()));
          BigDecimal average = t.getAverage();
          Long inflow =  t.getSuperInflow() + t.getMaxInflow() + t.getMiddleInflow() + t.getMinInflow();
          Long outflow =  t.getSuperOutflow() + t.getMaxOutflow() + t.getMiddleOutflow() + t.getMinOutflow();
          long l = inflow - outflow;
          //日净买入（手）
          t.setInflowBuy(l);
          //日流入
          BigDecimal inflowTotal = BigDecimal.valueOf(l).multiply(average);
          t.setInflowTotal(inflowTotal);

          //超大单
          BigDecimal superInflowAmount = average.multiply(BigDecimal.valueOf(t.getSuperInflow()));
          t.setSuperInflowAmount(superInflowAmount);
          BigDecimal superOutflowAmount = average.multiply(BigDecimal.valueOf(t.getSuperOutflow()));
          t.setSuperOutflowAmount(superOutflowAmount);

          //大单
          BigDecimal maxInflowAmount = average.multiply(BigDecimal.valueOf(t.getMaxInflow()));
          t.setMaxInflowAmount(maxInflowAmount);
          BigDecimal maxOutflowAmount = average.multiply(BigDecimal.valueOf(t.getMaxOutflow()));
          t.setMaxOutflowAmount(maxOutflowAmount);

          //中单
          BigDecimal middleInflowAmount = average.multiply(BigDecimal.valueOf(t.getMiddleInflow()));
          t.setMiddleInflowAmount(middleInflowAmount);
          BigDecimal middleOutflowAmount = average.multiply(BigDecimal.valueOf(t.getMiddleOutflow()));
          t.setMiddleOutflowAmount(middleOutflowAmount);

          //小单
          BigDecimal minInflowAmount = average.multiply(BigDecimal.valueOf(t.getMinInflow()));
          t.setMinInflowAmount(minInflowAmount);
          BigDecimal minOutflowAmount = average.multiply(BigDecimal.valueOf(t.getMinOutflow()));
          t.setMinOutflowAmount(minOutflowAmount);

          //根据主散比规则计算主力和散户数据
          if(queryData.getQueryParam("mainSubRate") != null){
              if(MainSubRate.NIE_EIGHT_TWO_ONE.name().equals(queryData.getQueryParam("mainSubRate"))){
                Double mainInflow =  t.getSuperInflow()*0.9 + t.getMaxInflow()*0.8 + t.getMiddleInflow()*0.2 + t.getMinInflow()*0.1;
                t.setMainInflow(mainInflow.longValue());
                t.setMainInflowAmount(average.multiply(BigDecimal.valueOf(t.getMainInflow())));
                Double mainOutflow =  t.getSuperOutflow()*0.9 + t.getMaxOutflow()*0.8 + t.getMiddleOutflow()*0.2 + t.getMinOutflow()*0.1;
                t.setMainOutflow(mainOutflow.longValue());
              }else{
                Double mainInflow =  t.getSuperInflow()*0.8 + t.getMaxInflow()*0.8 + t.getMiddleInflow()*0.2 + t.getMinInflow()*0.2;
                t.setMainInflow(mainInflow.longValue());
                t.setMainInflowAmount(average.multiply(BigDecimal.valueOf(t.getMainInflow())));
                Double mainOutflow =  t.getSuperOutflow()*0.8 + t.getMaxOutflow()*0.8 + t.getMiddleOutflow()*0.2 + t.getMinOutflow()*0.2;
                t.setMainOutflow(mainOutflow.longValue());
              }

          }else{
              Long mainInflow =  t.getSuperInflow() + t.getMaxInflow();
              t.setMainInflow(mainInflow);
              t.setMainInflowAmount(average.multiply(BigDecimal.valueOf(t.getMainInflow())));
              Long mainOutflow =  t.getSuperOutflow() + t.getMaxOutflow();
              t.setMainOutflow(mainOutflow);
          }
          t.setMainOutflowAmount(average.multiply(BigDecimal.valueOf(t.getMainOutflow())));
          Long subInflow = t.getSuperInflow() + t.getMaxInflow() + t.getMiddleInflow() + t.getMinInflow() - t.getMainInflow();
          t.setSubInflow(subInflow);
          t.setSubInflowAmount(average.multiply(BigDecimal.valueOf(subInflow)));
          Long subOutflow = t.getSuperOutflow() + t.getMaxOutflow() + t.getMiddleOutflow() + t.getMinOutflow() - t.getMainOutflow();
          t.setSubOutflow(subOutflow);
          t.setSubOutflowAmount(average.multiply(BigDecimal.valueOf(subOutflow)));
      });
     }
     Map<String,Object> results = new HashMap<>();
     results.put("mainSubRates",MainSubRate.values());
     return results;
    }


    @RequestMapping("analysisList")
    public ModelAndView analysisList(StockDayInformationVO queryData){
        ModelAndView mav = new ModelAndView(this.getModule() + "/analysisList");
        if(queryData.getStartDate() == null && queryData.getEndDate() == null){
            queryData.setStartDate(LocalDate.now().plusDays(-6));
        }
        PageInfo pageInfo = queryData.getPageInfo();
        if(pageInfo.getPageSize() > 100){
            pageInfo.setPageSize(10);
        }
        Page<StockDayInformationVO> page = new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize());
        Page<StockDayInformationVO> pageData;
        BigDecimal priceChange = queryData.getPriceChange();
        PriceChangeType priceChangeType = queryData.getPriceChangeType();
        if(priceChange != null && priceChange.doubleValue() != 0){
            queryData.setPriceChangeType(null);
            if(priceChange.doubleValue() > 0){
                pageData = stockDayInformationServiceImpl.selectLXZT(queryData, page);
            }else{
                pageData = stockDayInformationServiceImpl.selectLXDT(queryData, page);
            }
        }else{
            if(priceChange != null && priceChange.doubleValue() == 0){
                priceChangeType = PriceChangeType.FLAT;
            }
            if(priceChangeType == null){
                priceChangeType = PriceChangeType.LIMIT_UP;
            }
            if(priceChangeType.equals(PriceChangeType.RISE_ALL) || priceChangeType.equals(PriceChangeType.RISE) || priceChangeType.equals(PriceChangeType.LIMIT_UP)){
                if(priceChangeType.equals(PriceChangeType.RISE_ALL)){
                    queryData.setPriceChange(BigDecimal.ZERO);
                    queryData.setPriceChangeType(null);
                }
                pageData = stockDayInformationServiceImpl.selectLXZT(queryData, page);
            }else{
                pageData = stockDayInformationServiceImpl.selectLXDT(queryData, page);
            }
        }

        mav.addObject("pageData", pageData);
        mav.addObject("queryData", queryData);
        mav.addObject("PriceChangeTypes",PriceChangeType.values());
        return mav;
    }
}
