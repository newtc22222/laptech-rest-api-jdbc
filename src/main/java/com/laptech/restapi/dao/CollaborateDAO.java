package com.laptech.restapi.dao;

import java.util.List;

/**
 * collapse: discount, label
 *
 * @author Nhat Phi
 * @since (2023 - 01 - 30) 2022-11-22
 */
public interface CollaborateDAO<T1, T2> {
    int insert(T1 item_root_id, T2 item_add_id);

    int remove(T1 item_root_id, T2 item_remove_id);

    int updateMultiple(T1 item_root_id, List<T2> item_add_ids, List<T2> item_remove_ids);
}
