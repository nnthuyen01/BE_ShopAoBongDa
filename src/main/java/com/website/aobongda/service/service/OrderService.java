package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.dto.TotalPriceByStatus;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.OrderDetail;
import com.website.aobongda.model.Payment;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.User;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.OrderResponse;
import com.website.aobongda.repository.CartRepository;
import com.website.aobongda.repository.OrderDetailRepository;
import com.website.aobongda.repository.OrderRepository;
import com.website.aobongda.repository.PaymentRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.repository.VoucherRepository;
import com.website.aobongda.security.userprincipal.UserPrincipal;
import com.website.aobongda.service.impl.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	PaymentRepository paymentRepository;
	private Long price = (long) 0;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public DataResponse<?> create(OrderReq orderReq) {
		DataResponse<?> response = new DataResponse<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		User users = userRepository.getReferenceById(id);
		Order order = new Order();
		order.setName(users.getName());
		order.setPhone(users.getPhone());
		order.setAddress(orderReq.getAddress());
		order.setNote(orderReq.getNote());
		order.setDate(new Date());

		Voucher voucher = voucherRepository.findByCode(orderReq.getCode());
		if(voucher != null) {
			order.setCode(orderReq.getCode());
			order.setVoucher(voucher);
			order.setPriceOff(voucher.getPrice());
		}else {
			order.setPriceOff(Long.valueOf(0));
		}

		order.setUser(users);
		Payment payment = paymentRepository.getReferenceById(orderReq.getId_payment());
		order.setPayment(payment);

		order.setPriceShip(Long.valueOf(10000));
		order.setStatus(0);
		List<Cart> cartList = cartRepository.findByCartID_UserId(id);
		List<OrderDetail> orderDetails = new ArrayList<>();
		if (cartList.size() > 0) {
			cartList.forEach(cart -> {

				Product product = productRepository.getReferenceById(cart.getCartID().getProductId());
				price += (cart.getQuantity() * product.getPrice());
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(product);
				orderDetail.setQuantity(Long.valueOf(cart.getQuantity()));
				orderDetails.add(orderDetail);
				cartRepository.delete(cart);
			});
		}
		order.setTotalPriceOrigin(price);
		Long totalPrice = price + order.getPriceShip() - order.getPriceOff();
		order.setTotalPrice(totalPrice);
		orderRepository.save(order);
		orderDetails.forEach(orderDetail ->{
			orderDetail.setOrder(order);
			orderDetailRepository.save(orderDetail);
		});
		response.setSuccess(true);
		response.setMessage("Create successful order");
		return response;
	}

	
	@Override
	public DataResponse<OrderResponse> getAllOrders() {
		DataResponse<OrderResponse> response = new DataResponse<>();
		List<Order> orders = orderRepository.findAll();
		if(orders != null) {
			List<OrderResponse> listOrder = new ArrayList<>();
			for(Order order : orders) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				orderResponse.setStatus(order.getStatus());
				
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					listProduct.add(product);
				}
				
				orderResponse.setProducts(listProduct);
				listOrder.add(orderResponse);
			}
			response.setSuccess(true);
			response.setMessage("Success");
			response.setDatas(listOrder);
			
		}else {
			response.setSuccess(false);
			response.setMessage("Order is empty");
		}
		return response;
	}
	@Override
	public DataResponse<OrderResponse> getAllOrdersByUser(){
		DataResponse<OrderResponse> response = new DataResponse<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		List<Order> orders = orderRepository.findByIdUser(id);
		if(orders != null) {
			List<OrderResponse> listOrder = new ArrayList<>();
			for(Order order : orders) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				orderResponse.setStatus(order.getStatus());
				
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					listProduct.add(product);
				}
				
				orderResponse.setProducts(listProduct);
				listOrder.add(orderResponse);
			}
			response.setSuccess(true);
			response.setMessage("Success");
			response.setDatas(listOrder);
			
		}else {
			response.setSuccess(false);
			response.setMessage("Order is empty");
		}
		return response;
	}

	@Override
	public DataResponse<OrderResponse> getOrderById(Long id) {
		DataResponse<OrderResponse> response = new DataResponse<>();
		Order order = orderRepository.getById(id);
		if(order != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
			String us = order.getUser().getUsername();
			Long idtest = user.getId();
			if(user.getUsername().equals(order.getUser().getUsername()) 
					|| user.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				orderResponse.setStatus(order.getStatus());
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					product.setQuantity(orderDetail.getQuantity());
					listProduct.add(product);
				}
				orderResponse.setProducts(listProduct);
				response.setSuccess(true);
				response.setMessage("Success");
				response.setData(orderResponse);
			}else {
				response.setSuccess(false);
				response.setMessage("You do not have access");
			}
		}else {
			response.setSuccess(false);
			response.setMessage("Order not found");
		}
		return response;
	}
	
	@Override
	public DataResponse<?> changeStatus(Long id) {
		DataResponse<?> response = new DataResponse<>();
		Order order = orderRepository.getById(id);
		if(order != null) {
			if(order.getStatus() == 2) {
				response.setSuccess(false);
				response.setMessage("Order can't change status");
			}else {
				if(order.getStatus() == 0) {
					order.setStatus(1);
				}else if(order.getStatus() == 1){
					order.setStatus(2);
				}
				orderRepository.save(order);
				response.setSuccess(true);
				response.setMessage("Success");
			}
		}else {
			response.setSuccess(false);
			response.setMessage("Order not found");
		}
		return response;
	}

	@Override
	public DataResponse<TotalPriceByStatus> totalByStatus() {
		DataResponse<TotalPriceByStatus> response = new DataResponse<>();
		List<TotalPriceByStatus> total = orderRepository.totalByStatus();
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(total);
		return response;
	}
	
	@Override
	public DataResponse<OrderResponse> getOrderByStatus(int status) {
		DataResponse<OrderResponse> response = new DataResponse<>();
		List<Order> orders = orderRepository.getOrdersByStatus(status);
		if(orders.size()!=0) {
			List<OrderResponse> listOrder = new ArrayList<>();
			for(Order order : orders) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse.setAddress(order.getAddress());
				orderResponse.setName(order.getName());
				orderResponse.setPhone(order.getPhone());
				orderResponse.setUsername(order.getUser().getUsername());
				orderResponse.setDate(order.getDate());
				orderResponse.setStatus(status);
				orderResponse.setTotalPrice(order.getTotalPrice());
				orderResponse.setId(order.getId());
				
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					listProduct.add(product);
				}
				orderResponse.setProducts(listProduct);
				listOrder.add(orderResponse);
			}
			response.setSuccess(true);
			response.setMessage("Success");
			response.setDatas(listOrder);
			
		}else {
			response.setSuccess(false);
			response.setMessage("Order is empty");
		}
		return response;
	}
}
