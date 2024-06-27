package jsys.sales.web;

import javax.servlet.http.HttpServletRequest;

import jsys.sales.common.SalesBusinessException;
import jsys.sales.common.SalesSystemException;
import jsys.sales.entity.Customer;
import jsys.sales.logic.InsertCustomerLogic;

public class InsertCustomerAction {
	public String execute(HttpServletRequest request) {
		String page ="InsertCustomerView.jsp";

		// クライアントの入力値を取得する
		try {
		String customerCode = request.getParameter("CUSTOMER_CODE");
		String customerName = request.getParameter("CUSTOMER_NAME");
		String customerTelno = request.getParameter("CUSTOMER_TELNO");
		String customerPostalcode = request.getParameter("CUSTOMER_POSTALCODE");
		String customerAddress = request.getParameter("CUSTOMER_ADDRESS");
        int discountRate = Integer.parseInt(request.getParameter("DISCOUNT_RATE"));
        boolean deleteFlag = Boolean.parseBoolean(request.getParameter("DELETE_FLAG"));

		// 未入力の場合、エラーメッセージをリクエストスコープに設定し、エラーページを返却する。
		if(customerCode != null && customerCode.equals("")
			&& customerName != null && customerName.equals("")
			&& customerTelno != null && customerTelno.equals("")
			&& customerPostalcode != null && customerPostalcode.equals("")
			&& customerAddress != null && customerAddress.equals("")
			) {
			request.setAttribute("message", "いずれかのフィールドが未入力です。");
			return page;
		}

			InsertCustomerLogic logic = new InsertCustomerLogic();
			Customer customer = new Customer(customerCode, customerName, customerTelno,
																	customerPostalcode, customerAddress, discountRate,
																	deleteFlag);
			customer = logic.insertCustomer(customer);

			// 結果をリクエストスコープに設定する
			request.setAttribute("customer", customer);

			page = "/InsertCustomerResultView.jsp";
		} catch (SalesBusinessException e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		} catch (SalesSystemException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			// システムエラー画面を戻り値に設定する。
			page = "/SalesErrorView.jsp";
		}

		return page;

	}
}
