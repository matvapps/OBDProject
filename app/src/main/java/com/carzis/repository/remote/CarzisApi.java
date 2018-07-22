package com.carzis.repository.remote;

import com.carzis.model.response.BaseResponse;
import com.carzis.model.response.CarMetricResponse;
import com.carzis.model.response.CarResponse;
import com.carzis.model.response.ConfirmRegisterResponse;
import com.carzis.model.response.InfoResponse;
import com.carzis.model.response.ProfileResponse;
import com.carzis.model.response.RegisterResponse;
import com.carzis.model.response.TroubleResponse;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                                                  @NonNull @Query("sms_code") Integer code);

    @POST("auth")
    Call<ConfirmRegisterResponse> auth(@NonNull @Query("phone") String phone,
                                       @NonNull @Query("password") Integer password,
                                       @NonNull @Query("device_name") String deviceName,
                                       @NonNull @Query("device_id") String deviceId);

    @POST("delete_device")
    Call<BaseResponse> deleteDevice(@Header("token") String token,
                                    @NonNull @Query("device_id") String deviceId);

    @GET("profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token);



    @Multipart
    @POST("profile")
    Call<ProfileResponse> updateProfile(@Header("Authorization") String token,
                                        @Nullable @Query("email") String email,
                                        @Nullable @Query("first_name") String firstName,
                                        @Nullable @Query("second_name") String secondName,
                                        @Nullable @Query("birthday") String birthday,
                                        @Part MultipartBody.Part userImage);

    @POST("profile")
    Call<ProfileResponse> updateProfile(@Header("Authorization") String token,
                                        @Nullable @Query("email") String email,
                                        @Nullable @Query("first_name") String firstName,
                                        @Nullable @Query("second_name") String secondName,
                                        @Nullable @Query("birthday") String birthday);

    @POST("car_metric")
    Call<BaseResponse> addCarMetric(@Header("Authorization") String token,
                                    @Nullable @Query("car_id") String carId,
                                    @Nullable @Query("metric_code") String metricCode,
                                    @Nullable @Query("metric_value") String metricValue);

    @GET("metrics")
    Call<List<CarMetricResponse>> getCarMetrics(@Header("Authorization") String token,
                                                @Nullable @Query("car_id") String carId,
                                                @Nullable @Query("metric_code") String metricCode,
                                                @Nullable @Query("from") String from,
                                                @Nullable @Query("to") String to);


    @GET("load_dtc_description")
    Call<TroubleResponse> getTrouble(@Header("Authorization") String token,
                                     @NonNull @Query("code") String code,
                                     @NonNull @Query("lang") String lang,
                                     @NonNull @Query("brand") String brand);

    @GET("load_info_by_vin")
    Call<InfoResponse> getInfoByVin(@Header("Authorization") String token,
                                    @NonNull @Query("vin") String vin);

    @GET("load_info_by_auto_num")
    Call<InfoResponse> getInfoByNum(@Header("Authorization") String token,
                                 @NonNull @Query("number") String number);


    @GET("cars")
    Call<List<CarResponse>> getCars(@Header("Authorization") String token);


}
