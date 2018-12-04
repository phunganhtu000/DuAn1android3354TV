package dell.example.com.duan1android3354tv;

import dell.example.com.duan1android3354tv.model.Hit;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {
    @GET("api.php?latest")
    Call<Hit> getMyJSON();
}
