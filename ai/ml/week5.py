# See http://wiki.quantsoftware.org/index.php?title=CompInvesti_Homework_3
#
import argparse
#import csv
import pandas as pd
import numpy as np
from numpy import genfromtxt
import math
import copy
import QSTK.qstkutil.qsdateutil as du
import datetime as dt
import QSTK.qstkutil.DataAccess as da
import QSTK.qstkutil.tsutil as tsu
import QSTK.qstkstudy.EventProfiler as ep

"""
Week 5 problem (HW 3)
Market Simulator using orders.csv as input 
"""
class Portfolio:
	
	def getBalence(na_prices):
		return na_prices*self.stock +self.cash

def find_events(ls_symbols, d_data):
	''' Finding the event dataframe '''
	#df_close = d_data['close']
	df_close = d_data['actual_close']
	ts_market = df_close['SPY']

	print "Finding Events"

	# Creating an empty dataframe.. This tracks the date we want to do an event study for.. Set it all to blank by default
	df_events = copy.deepcopy(df_close)
	df_events = df_events * np.NAN

	# Time stamps for the event range
	ldt_timestamps = df_close.index

	for s_sym in ls_symbols:
		for i in range(1, len(ldt_timestamps)):
			# Calculating the returns for this timestamp
			f_symprice_today = df_close[s_sym].ix[ldt_timestamps[i]]
			f_symprice_yest = df_close[s_sym].ix[ldt_timestamps[i - 1]]
			f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
			f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
			f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
			f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1
			
			#
			# Event is if stock srops belwo $5
			if f_symprice_yest >= 9.0 and f_symprice_today<9.0:
				df_events[s_sym].ix[ldt_timestamps[i]] = 1

			# Event is found if the symbol is down more then 3% while the
			# market is up more then 2%
			#if f_symreturn_today <= -0.03 and f_marketreturn_today >= 0.02:
			#	df_events[s_sym].ix[ldt_timestamps[i]] = 1

	return df_events
	
def readInputFile(filename, dtype):
	na_data = genfromtxt(filename, dtype,delimiter=",") # Format year, month, day, symbol, buy/sell, # stocks
	#print na_data
	na_data = np.sort(na_data)
	#print "Sorted"
	#print na_data
	return na_data
	
def getQuotes(ls_symbols, ls_benchmarks, dt_start, dt_end=dt.datetime.now()):
	# We need closing prices so the timestamp should be hours=16.
	dt_timeofday = dt.timedelta(hours=16)

	# Get a list of trading days between the start and the end.
	ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt_timeofday)

	# Creating an object of the dataaccess class with Yahoo as the source.
	c_dataobj = da.DataAccess('Yahoo', cachestalltime=0)
	#append benchmarks
	ls_symbols = ls_symbols+ls_benchmarks
	print "Looking up ",ls_symbols
		
	# Lookup if any bad symbols
	ls_all_syms = c_dataobj.get_all_symbols()	
	# Bad symbols are symbols present in portfolio but not in all syms
	ls_bad_syms = list(set(ls_symbols) - set(ls_all_syms))

	if len(ls_bad_syms) != 0:
		print "Portfolio contains bad symbols : ", ls_bad_syms

	for s_sym in ls_bad_syms:
		i_index = ls_symbols.index(s_sym)
		ls_symbols.pop(i_index)
		lf_port_alloc.pop(i_index)

	# Keys to be read from the data, it is good to read everything in one go.
	ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']

	# Reading the data, now d_data is a dictionary with the keys above.
	# Timestamps and symbols are the ones that were specified before.
	ldf_data = c_dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
	d_data = dict(zip(ls_keys, ldf_data))
	
	#
	# TODO.. Scan prices for bad data (look for *2, *3, *.5, change in one day.. look for NaN etc.)
	

	# Getting the numpy ndarray of close prices.
	na_price = d_data['close'].values # adjusted close
	#na_price = d_data['actual_close'].values
	#print na_price
	saveWithTimeStamp("week5Prices.csv", ldt_timestamps, na_price)
	#na_prices = np.hstack((na_prices,np.ones(len(na_prices)).reshape(len(na_prices),1))) #Add cash price to na_prices.. assume = 1.0
	return na_price, ldt_timestamps

def saveWithTimeStamp(filename, ldt_timestamps, na_price):
	ts = np.array(ldt_timestamps, dtype = 'S16').reshape(len(ldt_timestamps),1)
	print "Merging Timestamps ",np.shape(ts)," and ",np.shape(na_price)
	po = np.hstack((ts, na_price))
	fmt = ''.join(['%s,' for x in range(len(na_price[0]))])+'%s'
	#print fmt
	#print po 
	np.savetxt(filename, po, fmt=fmt)
	
def impl(args):
	dtype = [('year', int), ('month', int), ('day', int), ('symbol', 'S15'), ('order', 'S10'), ('numStocks', int)]
	principal = args.p
	na_orders = readInputFile(args.i, dtype)
	#print na_orders
	
	dt_startDate = dt.datetime(na_orders[0][0], na_orders[0][1], na_orders[0][2])
	last = len(na_orders)-1
	dt_endDate = dt.datetime(na_orders[last][0], na_orders[last][1], na_orders[last][2])
	print  "Start = ",dt_startDate, " - ",dt_endDate
	ls_symbols = sorted(set(na_orders['symbol'])) #Sort and remove duplicates
	
	# Append Benchmarks to symbols
	ls_benchmarks = args.b.split(",")
	print "Benchmarks = ", ls_benchmarks
	
	na_allPrices, ldt_timestamps = getQuotes(ls_symbols, ls_benchmarks, dt_startDate, dt_endDate+dt.timedelta(days=1))
	
	na_prices = na_allPrices[:,0:len(ls_symbols)] # pull out stock prices
	na_benchmarks = na_allPrices[:,len(ls_symbols): ] # pul out benchmark prices
	na_prices = np.hstack((na_prices,np.ones(len(na_prices)).reshape(len(na_prices),1))) #Add cash price to na_prices.. assume = 1.0
	ls_symbols = ls_symbols+['CASH']
	print ls_symbols
	#printPrices(na_prices, ldt_timestamps)
	#Build portfolio matrix.
	rows = len(na_prices)
	#na_port = porfolio values to be calculated
	na_port = np.zeros((rows, len(na_prices[0]))) # +1 to allow for cash balance
	cashBalance = principal
	#na_port[0,len(na_port[0])-1] = principal #assign full principal to time0
	#print na_port
	ordersPointer =0 # keep track of which order we are on in na_orders
	todaysDate = dt_startDate
	idx = 0
	cashCol = len(na_port[0])-1 # note this assumes one col per stock even if no stock held for most of time.. and cash is last col
	#print "Checking dates ",todaysDate," <= ",dt_endDate," and ", idx, " <= ",(len(ldt_timestamps)-2) 
	while(todaysDate<=dt_endDate and idx<=len(ldt_timestamps)):
		todaysDate = ldt_timestamps[idx]# update date
		orders, ordersPointer, orderDate = getTodaysOrders(na_orders, todaysDate, ordersPointer)
		na_port[idx] = na_port[idx-1]
		if len(orders)>0:
			for order in orders:			
				tradeType = order['order']
				tradeSymbol = order['symbol']
				tradeNum = order['numStocks']
				tradePriceRow = na_prices[idx] 
				tradeCol = ls_symbols.index(tradeSymbol)
				tradePrice = tradePriceRow[tradeCol]
				print "On ",orderDate," a ",tradeType," order for ",tradeNum," shares of ",tradeSymbol, " was executed at ",tradePrice				
				if tradeType.upper() == "BUY":
					tradeTypeVal=1
				else:	
					tradeTypeVal=-1
				tradeCash = tradeTypeVal*tradePrice * tradeNum
				cashBalance = cashBalance - tradeCash 
				na_port[idx][tradeCol] = na_port[idx][tradeCol] + (tradeTypeVal * tradeNum)
				#update cash
				na_port[idx][cashCol] = cashBalance
		#print "Next loop Checking dates ",todaysDate," <= ",dt_endDate," and ", idx, " <= ",(len(ldt_timestamps)-2) 
		idx = idx+1
	
	na_portValueMatrix = na_prices*na_port
	#printPrices (na_portValueMatrix, ldt_timestamps)
	na_portValue = na_portValueMatrix.sum(axis=1)
	#print "Portfolio Value ",na_portValue
	saveWithTimeStamp("week5PortValue.csv", ldt_timestamps, np.array(na_portValue).reshape(len(na_portValue),1))
	calculateMetrics(na_portValue, "Portfolio")
	bStr = "Benchmark ",ls_benchmarks
	calculateMetrics(na_benchmarks, bStr)
	# this is numsharesA numSharesB .. numSharesN, Amount in Cash
	# Do a std multiple with na_prices..
	# in python matrix A * B is not matrix multiplication.. it is idndividual element multiplication
	
def myReturnize(nds):
	"""
	@summary Computes stepwise (usually daily) returns relative to 0, where
	0 implies no change in value.
	@return the array is revised in place
	"""
	s= np.shape(nds)
	print "Shape = ",s, " len(s) = ",len(s)
	if len(s)==1:
		nds=np.expand_dims(nds,1)
	print "Shape = ",s, " len(s) = ",len(s)
	print "nds = ",nds
	nds[1:, :] = (nds[1:, :] / nds[0:-1]) - 1
	print "nds = ",nds
	nds[0, :] = np.zeros(nds.shape[1])
	print "nds = ",nds
	
def calculateMetrics(na_portValue, name=""):	
	na_dailyRet = na_portValue.copy()
	#print na_dailyRet[1:]
	tsu.returnize0(na_dailyRet)
	#myReturnize(na_dailyRet[1:])
	#print "Daily REturns ",na_dailyRet
	na_portMeanDailyReturn = np.average(na_dailyRet)
	print "** Metrics ",name," **"
	print "Final value ",na_portValue[len(na_portValue)-1]
	print "Average Daily Return :", na_portMeanDailyReturn
	
	std = np.std(na_dailyRet)
	print "Volatility (stdev of daily returns):", std
	
	# Sharpe = K *dailyRet/ Std(DR)
	sharpe = np.sqrt(252) * na_portMeanDailyReturn / std
	print "Sharpe ratio :", sharpe

def printPrices(na_prices, ldt_timestamps):
	idx = 0
	for price in na_prices:
		print idx," ",ldt_timestamps[idx]," ",price
		idx=idx+1
	
def getTodaysOrders(na_orders, todaysDate, ordersPointer):
	orders = []
	if ordersPointer< len(na_orders):
		orderDate = dt.datetime(na_orders[ordersPointer][0],na_orders[ordersPointer][1],na_orders[ordersPointer][2])
	else:
		return [], ordersPointer, 0
	while ordersPointer< len(na_orders): # loop since there can be more than one trade per day
		nextOrder =  na_orders[ordersPointer]
		checkDate = dt.datetime(nextOrder[0],nextOrder[1],nextOrder[2])
		#print "Checking ",todaysDate," >= ",checkDate
		if(todaysDate>=checkDate):   # this order shoudl be fulfilled today
			#print "true"
			orders.append(nextOrder)
			ordersPointer = ordersPointer +1
		else:
			break
	return orders, ordersPointer, orderDate 
	
if __name__ == '__main__':
	parser = argparse.ArgumentParser(description='Market Simulator.')
	parser.add_argument('-p', type=int, default=10000, help='the initial amount to invest (Default 10000)')	
	parser.add_argument('-b', default='SPY', help='benchmark indices (comma seperated) to compare returns against')
	parser.add_argument('-i', default='week5Input.csv', help='the csv file with orders to process(default: orders.csv)')
	parser.add_argument('-o', default='output.csv', help='the output csv file with results (default: output.csv)')
	
	args = parser.parse_args()
	impl(args)
