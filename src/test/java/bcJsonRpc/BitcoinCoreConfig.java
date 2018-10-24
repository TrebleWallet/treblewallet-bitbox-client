package bcJsonRpc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Created by Robert Juhas on 10/24/2018
 */
public class BitcoinCoreConfig {

    public final static String clientPrivateKey1 = "92CqrxbHxU1nDQo2N9UkL6bGftcfbh929cYzPSubPshyERqhUK7";
    public final static String clientPublicKey1 = "04A48BED6D0C1FF608CFBC4F27D7831061A58C927055D0D74B3AD7351E3523D697785D50ACB87C57A472BB9AF8DC35CDC10302661FD301E9A58EC414105037A40F";
    public final static String clientAddress1 = "mofhdVSgsUsVacWsf8QMNhDQqYnVXPtnZH";

    public final static String clientPrivateKey3 = "92fdGNLkxzZBtVxaUoFMyU5ZFyJPv7uJrF3FwtBzckWXQqLMpve";
    public final static String clientPublicKey3 = "040D4B71CB6C94D6D0DECD497ADAE4ADA068F26C47A4CBAC51A3CB742F4EC713510924C789AB11D7F986CCF77AC6DB3B25B0216D607ADD7EC083D4B3B7EE098191";
    public final static String clientAddress3 = "myiatjHYvz2pAk2Z5QCu3chf5Gyu8FRumb";

    private BitcoindClientFactory clientFactory;
    private BitcoindInterface client;

    public BitcoinCoreConfig() throws IOException {
        clientFactory = new BitcoindClientFactory(new URL("http://localhost:18332/"),"test", "test");
        client = clientFactory.getClient();
    }

    public BitcoindInterface getClient() {
        return client;
    }
}
