package api.cobinhood.api;

import api.cobinhood.api.models.CobinResponse;
import api.cobinhood.api.models.chart.CandleResult;
import api.cobinhood.api.models.chart.Timeframe;
import api.cobinhood.api.models.market.CurrenciesResult;
import api.cobinhood.api.models.market.OrderBookResult;
import api.cobinhood.api.models.market.TickerResult;
import api.cobinhood.api.models.market.TradeResult;
import api.cobinhood.api.models.market.TradesResult;
import api.cobinhood.api.models.market.TradingPairsResult;
import api.cobinhood.api.models.market.TradingStatisticsResult;
import api.cobinhood.api.models.system.SystemInformationResult;
import api.cobinhood.api.models.system.SystemTimeResult;
import api.cobinhood.api.models.trading.OrderHistoryResult;
import api.cobinhood.api.models.trading.OrderItem;
import api.cobinhood.api.models.trading.OrderResult;
import api.cobinhood.api.models.trading.OrdersResult;
import api.cobinhood.api.models.wallet.DepositAddressesResult;
import api.cobinhood.api.models.wallet.BalancesResult;
import api.cobinhood.api.models.wallet.DepositResult;
import api.cobinhood.api.models.wallet.DepositsResult;
import api.cobinhood.api.models.wallet.LedgerResult;
import api.cobinhood.api.models.wallet.WithdrawalAddressesResult;
import api.cobinhood.api.models.wallet.WithdrawalResult;
import api.cobinhood.api.models.wallet.WithdrawalsResult;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static api.cobinhood.utils.Endpoints.CancelOrder;
import static api.cobinhood.utils.Endpoints.GetAllCurrencies;
import static api.cobinhood.utils.Endpoints.GetAllDeposits;
import static api.cobinhood.utils.Endpoints.GetAllOrders;
import static api.cobinhood.utils.Endpoints.GetAllTradingPairs;
import static api.cobinhood.utils.Endpoints.GetAllWithdrawals;
import static api.cobinhood.utils.Endpoints.GetCandles;
import static api.cobinhood.utils.Endpoints.GetDeposit;
import static api.cobinhood.utils.Endpoints.GetDepositAddresses;
import static api.cobinhood.utils.Endpoints.GetLedgerEntries;
import static api.cobinhood.utils.Endpoints.GetOrder;
import static api.cobinhood.utils.Endpoints.GetOrderBook;
import static api.cobinhood.utils.Endpoints.GetOrderHistory;
import static api.cobinhood.utils.Endpoints.GetOrderTrades;
import static api.cobinhood.utils.Endpoints.GetRecentTrades;
import static api.cobinhood.utils.Endpoints.GetSystemInformation;
import static api.cobinhood.utils.Endpoints.GetSystemTime;
import static api.cobinhood.utils.Endpoints.GetTicker;
import static api.cobinhood.utils.Endpoints.GetTrade;
import static api.cobinhood.utils.Endpoints.GetTradeHistory;
import static api.cobinhood.utils.Endpoints.GetTradingStatistics;
import static api.cobinhood.utils.Endpoints.GetWalletBalances;
import static api.cobinhood.utils.Endpoints.GetWithdrawal;
import static api.cobinhood.utils.Endpoints.GetWithdrawalAddresses;
import static api.cobinhood.utils.Endpoints.ModifyOrder;
import static api.cobinhood.utils.Endpoints.PlaceOrder;

/**
 * Created by joel on 3/19/18.
 */

public interface CobinhoodService {

    //Market API calls

    @GET(GetSystemTime)
    Single<CobinResponse<SystemTimeResult>> getSystemTime();

    @GET(GetSystemInformation)
    Single<CobinResponse<SystemInformationResult>> getSystemInformation();

    @GET(GetAllCurrencies)
    Single<CobinResponse<CurrenciesResult>> getAllCurrencies();

    @GET(GetAllTradingPairs)
    Single<CobinResponse<TradingPairsResult>> getAllTradingPairs();

    @GET(GetOrderBook)
    Single<CobinResponse<OrderBookResult>> getOrderBook(@Path("trading_pair_id") String tradingPairId, @Query("limit") Integer limit);

    @GET(GetTradingStatistics)
    Single<CobinResponse<TradingStatisticsResult>> getTradingStatistics();

    @GET(GetTicker)
    Single<CobinResponse<TickerResult>> getTicker(@Path("trading_pair_id") String tradingPairId);

    @GET(GetRecentTrades)
    Single<CobinResponse<TradesResult>> getRecentTrades(@Path("trading_pair_id") String tradingPairId, @Query("limit") Integer limit);

    @GET(GetCandles)
    Single<CobinResponse<CandleResult>> getCandles(@Path("trading_pair_id") String tradingPairId, @Path("timeframe")Timeframe timeframe,@Path("start_time") long startTime, @Path("end_time")long endTime);

    //Trading API calls

    @GET(GetOrder)
    Single<CobinResponse<OrderItem>> getOrder(@Path("order_id") String orderId);

    @GET(GetOrderTrades)
    Single<CobinResponse<TradesResult>> getOrderTrades(@Path("order_id") String orderId);

    @GET(GetAllOrders)
    Single<CobinResponse<OrdersResult>> getOrders(@Query("page") Integer page, @Query("limit") Integer limit);

    @POST(PlaceOrder)
    Single<CobinResponse<OrderResult>> placeOrder(@Body OrderItem order);

    @DELETE(CancelOrder)
    Single<CobinResponse> cancelOrder(@Path("order_id") String orderId);

    @GET(GetOrderHistory)
    Single<CobinResponse<OrderHistoryResult>> getOrderHistory(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET(GetTrade)
    Single<CobinResponse<TradeResult>> getTrade(@Path("trade_id") String tradeId);

    @PUT(ModifyOrder)
    Single<CobinResponse> modifyOrder(@Body OrderItem order, @Path("order_id") String orderId);

    @GET(GetTradeHistory)
    Single<CobinResponse<TradesResult>> getTradeHistory(@Query("page") Integer page, @Query("limit") Integer limit);

    //wallet API calls

    @GET(GetWalletBalances)
    Single<CobinResponse<BalancesResult>> getWalletBalances();

    @GET(GetLedgerEntries)
    Single<CobinResponse<LedgerResult>> getLedgerEntries(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET(GetDepositAddresses)
    Single<CobinResponse<DepositAddressesResult>> getDepositAddresses(@Query("currency") String currency);

    @GET(GetWithdrawalAddresses)
    Single<CobinResponse<WithdrawalAddressesResult>> getWithdrawalAddresses(@Query("currency") String currency);

    @GET(GetWithdrawal)
    Single<CobinResponse<WithdrawalResult>> getWithdrawal(@Path("withdrawal_id") String withdrawalId);

    @GET(GetAllWithdrawals)
    Single<CobinResponse<WithdrawalsResult>> getWithdrawals(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET(GetDeposit)
    Single<CobinResponse<DepositResult>> getDeposit(@Path("deposit_id") String depositId);

    @GET(GetAllDeposits)
    Single<CobinResponse<DepositsResult>> getDeposits();

}