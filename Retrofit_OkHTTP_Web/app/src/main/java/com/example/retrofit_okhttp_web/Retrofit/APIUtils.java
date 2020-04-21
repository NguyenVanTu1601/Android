package com.example.retrofit_okhttp_web.Retrofit;

/**
 * Cung cấp đường dẫn baseurl bên retrofit
 */
public class APIUtils {
    public static final String base_url = "http://192.168.1.5/Retrofit_QLSV/"; // đường dẫn là cố định là folder gốc chứa các file


    // Nhận và gửi dữ liệu
    /**
     * Khi DataClient viết 1 phương thức nào đó sẽ nhờ thằng này gửi lên. Khi có dữ liệu trả về, phương thức sẽ nhận và chứa lại vào Dataclient
     * Nói chung truyền nhận dữ liệu chứa trong DataClient
     * @return
     */
    public static  DataClient getData(){

        return Retrofit_Clien.getClient(base_url).create(DataClient.class);

    }
}
