package org.bitcoincore.request;

import java.util.List;

public class BitCoinCoreRequest {

    private String jsonrpc;
    private String id;
    private String method;
    private List<Object> params;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BitCoinCoreRequest{");
        sb.append("jsonrpc='").append(jsonrpc).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", params=").append(params);
        sb.append('}');
        return sb.toString();
    }
}
