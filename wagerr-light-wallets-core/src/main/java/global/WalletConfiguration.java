package global;

import org.wagerrj.core.Context;
import org.wagerrj.core.NetworkParameters;

/**
 * Created by furszy on 6/4/17.
 */

public interface WalletConfiguration {


    int getTrustedNodePort();

    String getTrustedNodeHost();

    String getOracleAddress();

    void saveTrustedNode(String host,int port);

    void saveScheduleBlockchainService(long time);

    long getScheduledBLockchainService();

    /**************   Constants   ***************/

    String getMnemonicFilename();

    String getWalletProtobufFilename();

    NetworkParameters getNetworkParams();

    String getKeyBackupProtobuf();

    long getWalletAutosaveDelayMs();

    Context getWalletContext();

    String getBlockchainFilename();

    String getCheckpointFilename();

    int getPeerTimeoutMs();

    long getPeerDiscoveryTimeoutMs();

    int getMinMemoryNeeded();

    long getBackupMaxChars();

    boolean isTest();

    int getProtocolVersion();
}
