package com.carzis.repository.remote;

import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.ConfirmRegisterResponse;
import com.carzis.model.response.ProfileResponse;
import com.carzis.model.response.RegisterResponse;
import com.carzis.model.response.TroubleResponse;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CarzisApi {

    @POST("register")
    Call<RegisterResponse> registerUser(@NonNull @Query("phone") String phone,
                                              @NonNull @Query("device_id") String deviceId,
                                              @NonNull @Query("device_name") String deviceName);

    @POST("resend_code")
    Call<BaseResponse> resendCode(@NonNull @Query("phone") String phone);

    @POST("confirm_register")
    Call<ConfirmRegisterResponse> confirmRegister(@NonNull @Query("phone") String phone,
                                             @NonNull @Query("code") String code);

    @POST("auth")
    Call<ConfirmRegisterResponse> auth(@NonNull @Query("phone") String phone,
                                             @NonNull @Query("password") String password,
                                             @NonNull @Query("device_id") String deviceId,
                                             @NonNull @Query("device_name") String deviceName);
    @POST("delete_device")
    Call<BaseResponse> deleteDevice(@Header("token") String token,
                                          @NonNull @Query("device_id") String deviceId);

    @GET("profile")
    Call<ProfileResponse> getProfile(@Header("token") String token);

    @GET("load_dtc_description")
    Call<TroubleResponse> getTrouble(@Header("token") String token,
                                     @NonNull @Query("code") String code,
                                     @NonNull @Query("lang") String lang,
                                     @NonNull @Query("brand") String brand);


}
