package com.laptech.restapi.payment.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.config.PartnerInfo;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Execute;

/**
 * @author hainguyen
 * @see <a href="https://developers.momo.vn">Documention</a>
 */

public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}
