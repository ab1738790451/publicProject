package com.woshen.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.server.IStockService;
import com.woshen.stock.server.IStockTimeSharingService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 股票走势表 前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Controller
@RequestMapping("stockTimeSharing")
public class StockTimeSharingController extends AbstractController<Integer, StockTimeSharing> {

   @Resource
   private IStockTimeSharingService stockTimeSharingServiceImpl;

   @Resource
   private IStockService stockServiceImpl;

   @Resource
   private IStockDayInformationService stockDayInformationServiceImpl;

   @Override
   public IStockTimeSharingService getService() {
   return stockTimeSharingServiceImpl;
   }

   @Override
   public Map<String, ?> afterList(HttpServletRequest request, HttpServletResponse response, StockTimeSharing queryData, Page<StockTimeSharing> pageData) {
    List<StockTimeSharing> records = pageData.getRecords();
    if(!CollectionUtils.isEmpty(records)){
        List<String> collect = records.stream().map(StockTimeSharing::getCode).distinct().collect(Collectors.toList());
        List<LocalDate> dates = records.stream().map(StockTimeSharing::getDate).distinct().collect(Collectors.toList());
        QueryWrapper<Stock> baseWrapper = stockServiceImpl.getBaseWrapper(new Stock());
        baseWrapper.in("code",collect);
        List<Stock> list = stockServiceImpl.list(baseWrapper);
        Map<String, String> map = list.stream().collect(Collectors.toMap(Stock::getCode, Stock::getName));
        QueryWrapper<StockDayInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("code",collect);
        queryWrapper.in("transaction_date",dates);
        List<StockDayInformation> stockDayInformations = stockDayInformationServiceImpl.list(queryWrapper);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, StockDayInformation> dayInformationMap = stockDayInformations.stream().collect(Collectors.toMap(t -> t.getCode() + "_" + t.getTransactionDate().format(dateTimeFormatter), t -> t));
        records.stream().forEach(t-> {
            t.setName(map.get(t.getCode()));
            StockDayInformation stockDayInformation = dayInformationMap.get(t.getCode() + "_" + t.getDate().format(dateTimeFormatter));
            if(stockDayInformation != null){
                 t.setLastClose(stockDayInformation.getClose());
                 t.setOpen(stockDayInformation.getOpen());
                 t.setClose(stockDayInformation.getTodayClose());
                 t.setHigh(stockDayInformation.getHigh());
                 t.setLow(stockDayInformation.getLow());
                 t.setPriceChange(stockDayInformation.getPriceChange());
            }
        });




    }
    return super.afterList(request, response, queryData, pageData);
   }
}
