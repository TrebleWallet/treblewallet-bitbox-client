package bcJsonRpc;

import bcJsonRpc.pojo.ListUnspentTO;
import bcJsonRpc.pojo.RawTransaction;
import bcJsonRpc.pojo.Vin;
import bcJsonRpc.pojo.Vout;
import org.bitcoinj.core.Coin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BitcoindUtils {

	/**
	 * Converts BitcoindClient4Java {@code BigDecimal} Bitcoins to {@code long} Satoshis.
	 *  
	 * @param bitcoinValue
	 * @return Satoshis
	 */
	public static long toSatoshi(BigDecimal bitcoinValue) {
		if (bitcoinValue == null) {
			return 0;
		}
		long satoshis = bitcoinValue.multiply(new BigDecimal(Coin.COIN.longValue())).longValue();
		return satoshis;
	}

	public static BigDecimal toBigDecimal(long value) {
		return new BigDecimal(value).divide(new BigDecimal(Coin.COIN.value)).setScale(8,RoundingMode.HALF_DOWN);
	}

	/**
	 * Sorts the coins by amount, smallest first.
	 * @param candidates
	 */
	public static void sortCoins(List<ListUnspentTO> candidates) {
		candidates.sort(new Comparator<ListUnspentTO>() {
			@Override
			public int compare(ListUnspentTO o1, ListUnspentTO o2) {
				BigDecimal value1 = o1.getAmount();
				BigDecimal value2 = o2.getAmount();
				int result = value1.compareTo(value2);
				return result;
			}
		});
	}

	/**
	 * Selects the coins after sorting small to big.
	 * 
	 * @param candidates
	 * @param amount
	 * @param fee
	 * @return
	 */
	public static List<ListUnspentTO> selectCoins(List<ListUnspentTO> candidates, long amount, long fee) {
		List<ListUnspentTO> utxos = new ArrayList<>();
		BitcoindUtils.sortCoins(candidates);
		long amountSoFar = 0;
		for (ListUnspentTO utxo : candidates) {
			amountSoFar += toSatoshi(utxo.getAmount());
			utxos.add(utxo);
			if (amountSoFar > amount + fee)
				break;
		}
		return utxos;
	}

	/**
	 * Sums the utxos.
	 * 
	 * @param utxos
	 * @return sum in Satoshis.
	 */
	public static long sum(List<ListUnspentTO> utxos) {
		long sum = 0;
		for (ListUnspentTO utxo : utxos) {
			sum += toSatoshi(utxo.getAmount());
		}
		return sum;
	}
	
	public static RawTransaction getParentTransaction(BitcoindInterface client, Vin vin) {
		RawTransaction parentTx = client.getrawtransaction(vin.getTxid(),true);
		return parentTx;
	}
	
	public static BigDecimal getFee(BitcoindInterface client, RawTransaction tx) {
		// sum the inputs
		BigDecimal sumInput = BigDecimal.ZERO;
		for (Vin vin: tx.getVin()) {
			RawTransaction parentTx = client.getrawtransaction(vin.getTxid(),true);
			Vout parentVout = parentTx.getVout().get(vin.getVout());
			sumInput = sumInput.add(parentVout.getValue());
		}
		// sum the outputs
		BigDecimal sumOutput = BigDecimal.ZERO;
		for(Vout vout:tx.getVout()) {
			sumOutput = sumOutput.add(vout.getValue());
		}
		// return sum(outputs)-sum(outputs)
		BigDecimal fee = sumInput.subtract(sumOutput);
		return fee;
	}

}
