package com.laptech.restapi.dao;

/**
 * collapse: discount, label
 *
 * @author Nhat Phi
 * @since (2023 - 01 - 30) 2022-11-22
 */
public interface CollaborateDAO<T1, T2> {
    int insert(T1 item1_id, T2 item2_id);

    int remove(T1 item1_id, T2 item2_id);
}
