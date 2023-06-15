package com.laptech.restapi.common.constant;

public class CsvHeader {
    public final static String[] bannerHeader = {"ID", "Đường dẫn ảnh", "Loại hình ảnh", "Tiêu đề (hiển thị)", "Link sản phẩm (click)", "Ngày sử dụng", "Ngày kết thúc", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] brandHeader = {"ID", "Tên thương hiệu", "Quốc gia", "Ngày thành lập", "Link logo", "Ngày tạo", "Ngày cập nhật", "Sản phẩm"};
    public final static String[] categoryHeader = {"ID", "Tên danh mục", "Link hình ảnh mô tả", "Mô tả danh mục", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] commentHeader = {"ID", "ID phản hồi", "ID sản phẩm", "Tên người dùng", "Số điện thoại", "Nội dung", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] discountHeader = {"ID", "Code", "Mức giảm", "Kiểu áp dụng", "Giá giảm tối đa", "Ngày áp dụng", "Ngày kết thúc", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] feedbackHeader = {"ID", "ID sản phẩm", "ID người dùng", "Tên người dùng", "Nội dung", "Điểm đánh giá", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] importProductHeader = {"ID", "ID sản phẩm", "Số lượng hàng hóa", "Giá nhập", "Ngày nhập", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] invoiceHeader = {"ID", "ID người dùng", "Tên người dùng", "Địa chỉ", "Số điện thoại", "Số lượng hàng hóa", "Phí vận chuyển", "Giá giảm", "Thuế", "Giá tổng", "Kiểu thanh toán", "Trạng thái thanh toán", "Trạng thái đơn hàng", "Ghi chú (bổ sung)", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] labelHeader = {"ID", "Tiêu đề", "Mô tả chung", "Mô tả chi tiết", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] productHeader = {"ID", "Thương hiệu", "Danh mục", "Tên sản phẩm", "Ngày ra mắt", "Số lượng kho", "Giá niêm yết", "Ngày tạo", "Ngày cập nhật"};
    public final static String[] roleHeader = {"ID", "Tên phân quyền", "Mô tả chức năng", "Ngày tạo", "Ngày cập nhật", "Danh sách người dùng"};
    public final static String[] userHeader = {"ID", "Tên đầy đủ", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Trạng thái tài khoản", "Ngày tạo", "Ngày cập nhật", "Phân quyền"};
}
