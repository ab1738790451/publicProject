package com.woshen.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.entity.StockTimeSharing;
import com.woshen.stock.server.IStockService;
import com.woshen.stock.server.IStockTimeSharingService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

   @Override
   public IStockTimeSharingService getService() {
   return stockTimeSharingServiceImpl;
   }

   @Override
   public Map<String, ?> afterList(HttpServletRequest request, HttpServletResponse response, StockTimeSharing queryData, Page<StockTimeSharing> pageData) {
    List<StockTimeSharing> records = pageData.getRecords();
    if(!CollectionUtils.isEmpty(records)){
     List<String> collect = records.stream().map(StockTimeSharing::getCode).distinct().collect(Collectors.toList());
     QueryWrapper<Stock> baseWrapper = stockServiceImpl.getBaseWrapper(new Stock());
     baseWrapper.in("code",collect);
     List<Stock> list = stockServiceImpl.list(baseWrapper);
     Map<String, String> map = list.stream().collect(Collectors.toMap(Stock::getCode, Stock::getName));
     records.stream().forEach(t-> t.setName(map.get(t.getCode())));
    }
    return super.afterList(request, response, queryData, pageData);
   }
}
