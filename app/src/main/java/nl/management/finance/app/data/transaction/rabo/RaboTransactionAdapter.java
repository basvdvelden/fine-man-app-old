package nl.management.finance.app.data.transaction.rabo;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.management.finance.app.data.Result;
import nl.management.finance.app.data.api.rabo.RaboApi;
import nl.management.finance.app.data.transaction.TransactionAdapter;
import nl.management.finance.app.data.transaction.TransactionDto;
import retrofit2.Call;
import retrofit2.Response;

public class RaboTransactionAdapter implements TransactionAdapter {
    private final static String TAG = "RaboTransactionAdapter";
    private final RaboApi api;

    public RaboTransactionAdapter(RaboApi api) {
        this.api = api;
    }

    @Override
    public Result<List<TransactionDto>> getTransactions(String bankAccountResourceId, UUID bankAccountId) {
        try {
            Call<RaboTransactions> call = api.getTransactions(bankAccountResourceId, "booked");
            Response<RaboTransactions> response = call.clone().execute();
            if (!response.isSuccessful()) {
                Exception error = new Exception(response.errorBody().string());
                Log.e(TAG, "error getting rabobank transactions", error);
                return new Result.Error(error);
            }
            return new Result.Success<>(toDtos(response.body(), bankAccountId));
        } catch (IOException e) {
            Log.e(TAG, "io error fetching rabo bank account transactions: ", e);
            return new Result.Error(e);
        }
    }

    private List<TransactionDto> toDtos(RaboTransactions raboTransactions, UUID bankAccountId) {
        List<TransactionDto> result = new ArrayList<>();
        List<RaboTransaction> bookedTransactions = raboTransactions.getTransactions().getBooked();
        for (RaboTransaction bookedTransaction : bookedTransactions) {
            Log.e(TAG, bookedTransaction.toString());
            result.add(new TransactionDto(
                    bankAccountId,
                    bookedTransaction.getCheckId(), "booked",
                    bookedTransaction.getBookingDate(),
                    bookedTransaction.getDebtorName(),
                    bookedTransaction.getUltimateDebtor(),
                    bookedTransaction.getCreditorName(),
                    bookedTransaction.getUltimateCreditor(),
                    bookedTransaction.getTransactionAmount().getAmount(),
                    bookedTransaction.getRemittanceInformationStructured(),
                    bookedTransaction.getInitiatingPartyName()
            ));
        }

        return result;
    }
}
