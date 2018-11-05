import QSTK.qstkutil.qsdateutil as du
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkutil.DataAccess as da

import datetime as dt
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

#
# Generate best allocation (based on best sharpe ration) for a portfolio of 4 for a period (Buy at start of period, hold til end of period)
#
#

def genBestAlloc(dt_start, dt_end, na_portfolio):
	dt_timeofday = dt.timedelta(hours=16)
	ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt_timeofday)
	#Get Data
	c_dataobj = da.DataAccess('Yahoo', cachestalltime=0)
	ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
	ldf_data = c_dataobj.get_data(ldt_timestamps, na_portfolio, ls_keys)
	d_data = dict(zip(ls_keys, ldf_data))
	allocs = np.zeros((10000,4))	
	n=0
	bestSharpe = 0
	# todo make general for n shares... 
	for i in range(0,11):
		for j in range(0,11):
			for k in range(0,11):
				for l in range(0,11):
					alloc = [i/10.0,j/10.0,k/10.0,l/10.0]
					if sum(alloc)==1.0:
						allocs[n,:]=alloc
						dt_start, dt_end, na_portfolio, lf_port_alloc, na_portMeanDailyReturn, std, sharpe = simulateForAlloc(d_data, dt_start, dt_end, na_portfolio, alloc)
						if sharpe > bestSharpe:
							bestSharpe = sharpe
							bestAlloc = lf_port_alloc
							bestAdr = na_portMeanDailyReturn
							bestVolatility = std
						n = n+1
	allocs = allocs[0:n,:]
	#print "Allocs ..."
	#print allocs
	#print n
		
	return dt_start, dt_end, na_portfolio, bestAlloc, bestAdr, bestVolatility, bestSharpe

def simulateForAlloc(d_data, dt_start, dt_end, na_portfolio, lf_port_alloc):	
	# Get close values
	na_price = d_data['close'].values
	#print "Closing prices :", na_price
	na_normalized_price = na_price / na_price[0, :]
	#np.savetxt("price.csv", na_price, delimiter=",")
	
	# Copying close price into separate dataframe to find rets	
	na_rets = na_normalized_price.copy()

	# Numpy matrix of filled data values
	#na_rets = df_rets.values
	# returnize0 works on ndarray and not dataframes.
	#tsu.returnize0(na_rets)
	#print "Size of na_rets:",na_rets.shape

	# Estimate portfolio returns
	na_portrets = np.sum(na_rets * lf_port_alloc, axis=1)
	#print "Size of na_portrets:",na_portrets.shape
	na_port_total = np.cumprod(na_portrets + 1)
	na_component_total = np.cumprod(na_rets + 1, axis=0)
	na_dailyRet = na_portrets.copy()
	# Get  daily return using returnize function (does (an/an-1) -1 over all n)
	tsu.returnize0(na_dailyRet)
	
	# Avg Daily Return 
	#np.savetxt("portDailyRet.csv", na_dailyRet, delimiter=",")
	na_portMeanDailyReturn = np.average(na_dailyRet)
	#print "Average Daily Return :", na_portMeanDailyReturn
	
	std = np.std(na_dailyRet)
	#print "Volatility (stdev of daily returns):", std
	
	
	# Sharpe = K *dailyRet/ Std(DR)
	sharpe = np.sqrt(252) * na_portMeanDailyReturn / std
	#print "Sharpe ratio :", sharpe

	return dt_start, dt_end, na_portfolio, lf_port_alloc, na_portMeanDailyReturn, std, sharpe
	
# Should return Volatility (stdDev), daily_ret, sharpe,  cum_ret
def simulate(dt_start, dt_end, na_portfolio, lf_port_alloc):
	dt_timeofday = dt.timedelta(hours=16)
	ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt_timeofday)
	#Get Data
	c_dataobj = da.DataAccess('Yahoo', cachestalltime=0)
	ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
	ldf_data = c_dataobj.get_data(ldt_timestamps, na_portfolio, ls_keys)
	d_data = dict(zip(ls_keys, ldf_data))	
	# Get close values
	na_price = d_data['close'].values
	#print "Closing prices :", na_price
	na_normalized_price = na_price / na_price[0, :]
	#np.savetxt("price.csv", na_price, delimiter=",")
	
	# Copying close price into separate dataframe to find rets	
	na_rets = na_normalized_price.copy()

	# Numpy matrix of filled data values
	#na_rets = df_rets.values
	# returnize0 works on ndarray and not dataframes.
	#tsu.returnize0(na_rets)
	#print "Size of na_rets:",na_rets.shape

	# Estimate portfolio returns
	na_portrets = np.sum(na_rets * lf_port_alloc, axis=1)
	#print "Size of na_portrets:",na_portrets.shape
	na_port_total = np.cumprod(na_portrets + 1)
	na_component_total = np.cumprod(na_rets + 1, axis=0)
	na_dailyRet = na_portrets.copy()
	tsu.returnize0(na_dailyRet)
	
	# Avg Daily Return 
	#np.savetxt("portDailyRet.csv", na_dailyRet, delimiter=",")
	na_portMeanDailyReturn = np.average(na_dailyRet)
	#print "Average Daily Return :", na_portMeanDailyReturn
	
	std = np.std(na_dailyRet)
	#print "Volatility (stdev of daily returns):", std
	
	
	# Sharpe = K *dailyRet/ Std(DR)
	sharpe = np.sqrt(252) * na_portMeanDailyReturn / std
	#print "Sharpe ratio :", sharpe

	return dt_start, dt_end, na_portfolio, lf_port_alloc, na_portMeanDailyReturn, std, sharpe
	
def printout(dt_start, dt_end, na_portfolio, lf_port_alloc, na_portMeanDailyReturn, std, sharpe):
	print "Start Date:", dt_start
	print "End Date:", dt_end
	print "Symbols:", na_portfolio
	print "Alloc:",lf_port_alloc
	print "Average Daily Return :", na_portMeanDailyReturn
	print "Volatility (stdev of daily returns):", std
	print "Sharpe ratio :", sharpe
	
def main():
	#ls_symbols = ['GOOG','AAPL', 'GLD', 'XOM']	
	ls_symbols = ['BRCM', 'ADBE', 'AMD', 'ADI'] 
	#alloc = [0.0, 0.4, 0.4, 0.2]
	#alloc = [0.0, 0.0, 0.0, 1.0]
	dt_start = dt.datetime(2011, 1, 1)
	dt_end = dt.datetime(2011, 12, 31)
	
	dt_start, dt_end, na_portfolio, bestAlloc, bestAdr, bestVolatility, bestSharpe = genBestAlloc(dt_start, dt_end, ls_symbols)
	printout (dt_start, dt_end, na_portfolio, bestAlloc, bestAdr, bestVolatility, bestSharpe )

	
	
if __name__ == '__main__':
    main()