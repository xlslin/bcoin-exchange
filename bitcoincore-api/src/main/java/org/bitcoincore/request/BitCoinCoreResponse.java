package org.bitcoincore.request;

public class BitCoinCoreResponse<T> {

    private String id;
    private BitCoinCoreResponseError error;
    private T result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BitCoinCoreResponseError getError() {
        return error;
    }

    public void setError(BitCoinCoreResponseError error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BitCoinCoreResponse{");
        sb.append("id='").append(id).append('\'');
        sb.append(", error=").append(error);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
