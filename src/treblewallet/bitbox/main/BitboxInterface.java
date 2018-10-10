package treblewallet.bitbox.main;

import java.util.List;

import treblewallet.bitbox.main.pojo.HashKeyPathDTO;
import treblewallet.bitbox.main.pojo.InfoDTO;
import treblewallet.bitbox.main.pojo.PubKeyDTO;
import treblewallet.bitbox.main.pojo.PubKeyPathDTO;
import treblewallet.bitbox.main.pojo.SignDTO;

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
