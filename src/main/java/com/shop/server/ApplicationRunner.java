package com.shop.server;

import com.shop.server.dao.BankCardDao;
import com.shop.server.dao.CartDao;
import com.shop.server.dao.OrderDao;
import com.shop.server.dao.PaymentDao;
import com.shop.server.dao.ProductDao;
import com.shop.server.dao.ProductItemDao;
import com.shop.server.dao.PromoCodeDao;
import com.shop.server.dao.UserDao;
import com.shop.server.model.dto.ProductFilter;
import com.shop.server.model.entity.BankCard;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.Order;
import com.shop.server.model.entity.Payment;
import com.shop.server.model.entity.Product;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.entity.PromoCode;
import com.shop.server.model.entity.User;
import com.shop.server.model.type.Category;
import com.shop.server.model.type.Material;
import com.shop.server.utils.sql.PromoCodeSql;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class ApplicationRunner {
    public static void main(String[] args) {
        var instance = PaymentDao.getInstance();
        var all = instance.findAll();
        System.out.println(all);
    }
}
