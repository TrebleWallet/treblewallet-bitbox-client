package treblewallet.bitbox;

import java.util.List;

import treblewallet.bitbox.pojo.HashKeyPathDTO;
import treblewallet.bitbox.pojo.InfoDTO;
import treblewallet.bitbox.pojo.PubKeyDTO;
import treblewallet.bitbox.pojo.PubKeyPathDTO;
import treblewallet.bitbox.pojo.SignDTO;

/*
 * Created by Robert Juhas on 7/24/2018
 */
public interface BitboxInterface {

	SignDTO sign(String curve, String meta, List<HashKeyPathDTO> hasharray, List<PubKeyPathDTO> pubkey);

	/**
	 * Get the public key for a {@code keyPath}.
	 * 
	 * @param keyPath
	 *            BIP44 {@code keyPath}. e.g. m/44'/1'/0'/0/1
	 * @return
	 * @throws BitBoxException
	 */
	PubKeyDTO xpub(String keypath);

	/**
	 * Get the Bitbox info.
	 * 
	 * @return
	 * @throws BitBoxException
	 */
	InfoDTO info();
}
