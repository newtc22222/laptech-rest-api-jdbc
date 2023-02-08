package com.laptech.restapi.common.enums;

/**
 * <ul>
 *     <li>PENDING - Đơn hàng ở trạng thái đặt hàng (chờ xác nhận phía khách)</li>
 *     <li>WAIT_CONFIRMED - Đơn hàng đang chờ được người bán hàng xác nhận</li>
 *     <li>PREPARED - Đơn hàng đang được chuẩn bị giao (phía người bán)</li>
 *     <li>SHIPPED - Đơn hàng đang được giao</li>
 *     <li>RECEIVED - Đơn hàng đã được nhận</li>
 *     <li>CANCELED - Đơn hàng bị hủy phía khách hàng</li>
 *     <li>FAILED - Đơn hàng không thể hoàn thành do sự cố</li>
 *     <li>IGNORED - Đơn hàng lỗi hoặc từ chối giao hàng, bị bỏ qua</li>
 * </ul>
 *
 * @author Nhat Phi
 * @version 1.2
 * @since 2022-11-18 (update 2023-02-02)
 */
public enum OrderStatus {
    PENDING,
    WAIT_CONFIRMED,
    PREPARED,
    SHIPPED,
    RECEIVED,
    CANCELED,
    FAILED,
    IGNORED
}
