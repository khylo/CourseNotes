'''
Technical Indicators
'''

import pandas as pd
import numpy as np
import math
import copy
import argparse
import QSTK.qstkutil.qsdateutil as du
import datetime as dt
import QSTK.qstkutil.DataAccess as da
import QSTK.qstkutil.tsutil as tsu
import matplotlib.pyplot as plt
import QSTK.qstkstudy.EventProfiler as ep

"""
Accepts a list of symbols along with start and end date
Returns the Event Matrix which is a pandas Datamatrix
Event matrix has the following structure :
	|IBM |GOOG|XOM |MSFT| GS | JP |
(d1)|nan |nan | 1  |nan |nan | 1  |
(d2)|nan | 1  |nan |nan |nan |nan |
(d3)| 1  |nan | 1  |nan | 1  |nan |
(d4)|nan |  1 |nan | 1  |nan |nan |
...................................
...................................
Also, d1 = start date
nan = no information about any event.
1 = status bit(positively confirms the event occurence)
"""

def stockDropsBelow5(**kwargs):
	#print kwargs
	ls_symbols=kwargs['symbols']
	d_data = kwargs['data']
	df_adjClose = d_data['close']
	#df_adjClose = d_data['actual_close']
	ldt_timestamps = df_adjClose.index	
	#extend so we can place sell orders in future
	convertedLdtTimestamp = map(convertLdtTimestamp,ldt_timestamps)
	#Market price
	ts_market = df_adjClose['SPY']
	ls_orders = []	
	# Creating an empty dataframe.. This tracks the date we want to do an event study for.. Set it all to blank by default
	df_events = copy.deepcopy(df_adjClose)
	df_events = df_events * np.NAN
	for s_sym in ls_symbols:
		for i in range(1, len(ldt_timestamps)):
			# Calculating the returns for this timestamp
			f_symprice_today = df_adjClose[s_sym].ix[ldt_timestamps[i]]
			f_symprice_yest = df_adjClose[s_sym].ix[ldt_timestamps[i - 1]]
			f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
			f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
			f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
			f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

			if (f_symprice_yest >= 5.0 and f_symprice_today<5.0):			
				df_events[s_sym].ix[ldt_timestamps[i]] = 1
				ls_orders = ls_orders + [convertedLdtTimestamp[i]+[ s_sym, 'BUY', 100]] # split timestamp into yyyymmdd
				if(i+5>=len(ldt_timestamps)):
					ls_orders = ls_orders + [convertedLdtTimestamp[len(ldt_timestamps)-1]+[s_sym, 'SELL', 100]]
				else:
					ls_orders = ls_orders + [convertedLdtTimestamp[i+5]+[s_sym, 'SELL', 100]]
				
	return df_events, ls_orders	
	
def stockBigMovement(**kwargs):
	#print kwargs
	ls_symbols=kwargs['symbols']
	d_data = kwargs['data']
	#df_adjClose = d_data['close']
	df_adjClose = d_data['actual_close']
	ldt_timestamps = df_adjClose.index	
	#extend so we can place sell orders in future
	convertedLdtTimestamp = map(convertLdtTimestamp,ldt_timestamps)
	#Market price
	ts_market = df_adjClose['SPY']
	ls_orders = []	
	# Creating an empty dataframe.. This tracks the date we want to do an event study for.. Set it all to blank by default
	df_events = copy.deepcopy(df_adjClose)
	df_events = df_events * np.NAN
	for s_sym in ls_symbols:
		for i in range(1, len(ldt_timestamps)):
			# Calculating the returns for this timestamp
			f_symprice_today = df_adjClose[s_sym].ix[ldt_timestamps[i]]
			f_symprice_yest = df_adjClose[s_sym].ix[ldt_timestamps[i - 1]]
			f_marketprice_today = ts_market.ix[ldt_timestamps[i]]
			f_marketprice_yest = ts_market.ix[ldt_timestamps[i - 1]]
			f_symreturn_today = (f_symprice_today / f_symprice_yest) - 1
			f_marketreturn_today = (f_marketprice_today / f_marketprice_yest) - 1

			if ((f_symprice_today - f_symprice_yest)/ f_symprice_today >= 0.1):			# 10% move up
				df_events[s_sym].ix[ldt_timestamps[i]] = 1
				ls_orders = ls_orders + [convertedLdtTimestamp[i]+[ s_sym, 'BUY', 100]] # split timestamp into yyyymmdd
				if(i+5>=len(ldt_timestamps)):
					ls_orders = ls_orders + [convertedLdtTimestamp[len(ldt_timestamps)-1]+[s_sym, 'SELL', 100]]
				else:
					ls_orders = ls_orders + [convertedLdtTimestamp[i+5]+[s_sym, 'SELL', 100]]
				
	return df_events, ls_orders	
	
def boll(**kwargs):
	'''
	Bollinger Bands TI.
	'''
	#print kwargs
	ls_symbols=kwargs['symbols']
	d_data = kwargs['data']
	
	df_adjClose = d_data['close']
	#df_close = d_data['actual_close']
	ldt_timestamps = df_adjClose.index	
	if 'rollingPeriod' in kwargs:
		rollingPeriod = kwargs['rollingPeriod']
	else:
		rollingPeriod = 20
	#extend so we can place sell orders in future
	convertedLdtTimestamp = map(convertLdtTimestamp,ldt_timestamps)	
	# return character is a dict of dataframes
	ret = {}
	for s_sym in ls_symbols:
		df_boll = pd.DataFrame({'price':df_adjClose[s_sym].values}, index=ldt_timestamps)
		df_boll['mean'] = pd.rolling_mean(df_adjClose[s_sym].values,rollingPeriod)
		#print "**",df_boll
		df_boll['std'] = pd.rolling_std(df_adjClose[s_sym].values,rollingPeriod)
		df_boll['boll'] = (df_boll['price']-df_boll['mean']) / df_boll['std']
		df_boll['boll+'] = df_boll['mean'] + df_boll['std']
		df_boll['boll-'] = df_boll['mean'] - df_boll['std']
		ret[s_sym]=df_boll
				
	#print "d_metrics[",ret.keys()[0],"] = ",ret[ret.keys()[0]]
	if(not('noGraph' in kwargs)):
		generateBollGraph(ret)				
	return ret	

def macd(**kwargs):
	'''
	MACD (Moving Average Convergence Divergence) TI.
	see http://en.wikipedia.org/wiki/MACD
	'''
	#print kwargs
	ls_symbols=kwargs['symbols']
	d_data = kwargs['data']
	
	df_adjClose = d_data['close']
	#df_close = d_data['actual_close']
	ldt_timestamps = df_adjClose.index	
	if 'rollingPeriod1' in kwargs:
		rollingPeriod1 = kwargs['rollingPeriod1']
	else:
		rollingPeriod1 = 12
	if 'rollingPeriod2' in kwargs:
		rollingPeriod2 = kwargs['rollingPeriod2']
	else:
		rollingPeriod2 = 26
	#extend so we can place sell orders in future
	convertedLdtTimestamp = map(convertLdtTimestamp,ldt_timestamps)	
	# return character is a dict of dataframes
	ret = {}
	for s_sym in ls_symbols:
		df_macd = pd.DataFrame({'price':df_adjClose[s_sym].values}, index=ldt_timestamps)
		df_macd['mean1'] = pd.rolling_mean(df_adjClose[s_sym].values,rollingPeriod1)
		df_macd['mean2'] = pd.rolling_mean(df_adjClose[s_sym].values,rollingPeriod2)
		ret[s_sym]=df_macd
		
	print "d_metrics[",ret.keys()[0],"] = ",ret[ret.keys()[0]]
	if(not('noGraph' in kwargs)):
		generateMacdGraph(ret)	
				
	return ret	

def find_bollEvents(ls_symbols, d_metrics, d_data):
	''' Finding the event dataframe '''
	df_events = copy.deepcopy(d_data['actual_close'])	
	df_events = df_events * np.NAN
	ls_orders = []	
	ldt_timestamps = d_data['actual_close'].index
	convertedLdtTimestamp = map(convertLdtTimestamp,ldt_timestamps)
	numEvents =0
	for sym in d_metrics:
		yest = np.NAN
		for i, dt in enumerate(d_metrics[sym].index):
			# Calculating the returns for this timestamp
			f_marketboll_today = d_metrics['SPY']['boll'][dt]	
			if(not pd.isnull(yest) and not pd.isnull(d_metrics[sym]['boll'][yest])):
				f_boll_today = d_metrics[sym]['boll'][dt]			
				f_boll_yest = d_metrics[sym]['boll'][yest]
				if (f_boll_today < -2.0 and f_boll_yest>=-2.0 and f_marketboll_today >=1.1):
					numEvents = numEvents +1
					df_events[sym].ix[dt] = 1
					ls_orders = ls_orders + [convertedLdtTimestamp[i]+[ sym, 'BUY', 100]] # split timestamp into yyyymmdd
					if(i+5>=len(ldt_timestamps)):
						ls_orders = ls_orders + [convertedLdtTimestamp[len(ldt_timestamps)-1]+[sym, 'SELL', 100]]
					else:
						ls_orders = ls_orders + [convertedLdtTimestamp[i+5]+[sym, 'SELL', 100]]
			yest = dt
			
			
				#ls_orders = ls_orders + [convertedLdtTimestamp[i]+[ s_sym, 'BUY', 100]] # split timestamp into yyyymmdd
				#if(i+5>=len(ldt_timestamps)):
				#	ls_orders = ls_orders + [convertedLdtTimestamp[len(ldt_timestamps)-1]+[s_sym, 'SELL', 100]]
				#else:
				#	ls_orders = ls_orders + [convertedLdtTimestamp[i+5]+[s_sym, 'SELL', 100]]
 	print numEvents," events detected. Note check pdf chart for exact number for questions minus those at extremities of timeline"
	return df_events, ls_orders	
			
				

def find_events(ls_symbols, d_data, event):
	''' Finding the event dataframe '''
	#df_adjClose = d_data['close']
	df_adjClose = d_data['actual_close']

	print "Finding Events"

	# Creating an empty dataframe.. This tracks the date we want to do an event study for.. Set it all to blank by default
	df_events = copy.deepcopy(df_adjClose)
	df_events = df_events * np.NAN

	# Time stamps for the event range
	df_events, ls_orders = event(data=d_data, symbols=ls_symbols)

	return df_events, ls_orders
	
# Turn ldt_timestamp (2012-04-24 16:00:00) into 2012,04,24	
def convertLdtTimestamp(t):
	#print "Formating ",t," ",type(t)," to ",t.strftime("%Y,%m,%d").split(",")
	return t.strftime("%Y,%m,%d").split(",")

def saveToFile(filename, ls_orders):
	fmt = ''.join(['%s,' for x in range(len(ls_orders[0]))])
	#print fmt
	#print po 
	print "Saving orders file ",filename
	np.savetxt(filename, ls_orders, fmt=fmt)
	
def generateBollGraph(d_metrics):
	# Plotting the prices with x-axis=timestamps
	plt.clf()
	keys = "-".join(d_metrics.keys())
	for key in d_metrics:
		plt.plot(d_metrics[key].index, d_metrics[key]['price'])
		plt.plot(d_metrics[key].index, d_metrics[key]['mean'])
		plt.fill_between(d_metrics[key].index,d_metrics[key]['boll-'],d_metrics[key]['boll+'],color='0.8')
		#plt.plot(d_metrics[key].index, d_metrics[key]['boll+'])
		#plt.plot(d_metrics[key].index, d_metrics[key]['boll-'])
		oldBol=np.NaN
		for bolRow in d_metrics[key].iterrows():
			bol=bolRow[1]['boll']
			print "bol = ",bolRow[0], " ",bol
			if(oldBol<1 and bol>=1):# breaking through top line
				plt.axvline(bolRow[0]) # bolRow[0] = index = date 
			elif (oldBol>-1 and bol<=-1):# breaking through bottom  line
				plt.axvline(bolRow[0]) 
			oldBol = bol
			
	plt.legend(d_metrics.keys()+" Boll")
	plt.ylabel('Adjusted Close')
	plt.xlabel('Date')
	filename = 'boll_'+keys+'.pdf'
	print "Saving Graphs ",filename
	plt.savefig(filename, format='pdf')
	
def generateMacdGraph(d_metrics):
	#todo
	print "TODO"
	
def init(args):
	dt_start = dt.datetime.strptime(args.s, dateFmt)
	dt_end = dt.datetime.strptime(args.e, dateFmt) 
	print  "Start = ",dt_start, " - ",dt_end
	ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))
	#ldt_timestampsExt = du.getNYSEdays(dt_start, dt_end+dt.timedelta(days=7), dt.timedelta(hours=16))# append 7 days on to cater for future sell orders

	dataobj = da.DataAccess('Yahoo')
	if(args.sl != None):
		ls_symbols = (args.sl).split(",")
	else:	
		ls_symbols = dataobj.get_symbols_from_list(args.sIdx)
		
	
	ls_symbols.append('SPY')
	print "How many symbols? ",len(ls_symbols)
	#print ls_symbols
	ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
	ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys) # big command to get all data for symbols	
	d_data = dict(zip(ls_keys, ldf_data))
	
	#boll
	d_metrics = boll(data=d_data, symbols=ls_symbols, noGraph='y') # dict with symbol as key and DataFrame as data
	print "Creating Study"
	
	df_events, ls_orders = find_bollEvents(ls_symbols, d_metrics, d_data)
	fn = 'MyBollEventStudy.pdf'
	print "Generating eventStudy ",fn
	ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
				s_filename=fn, b_market_neutral=True, b_errorbars=True,
				s_market_sym='SPY')
	
	# HW7 extension.. Add orders output file
	print "Saving ",len(ls_orders)," ordersOut.csv"
	saveToFile("ordersOut.csv", ls_orders)
	


if __name__ == '__main__':
	today = dt.datetime.now()
	dateFmt = "%Y-%m-%d"
	defStartDt = today-dt.timedelta(days=10)
	defStart=defStartDt.strftime(dateFmt)
	defEnd=today.strftime(dateFmt)

	parser = argparse.ArgumentParser(description='Market Simulator.')
	parser.add_argument('-p', type=int, default=10000, help='the initial amount to invest (Default 10000)')	
	#todo add -sl to manually add list.. make optional
	#parser.add_argument('-sl', default='sp5002012', help='List of symbols')
	parser.add_argument('-sIdx', default='sp5002012', help='List of symbols')
	parser.add_argument('-s', default=defStart, help='startDate (yyyy-mm-dd)')
	parser.add_argument('-sl', nargs='?', default=None, const=None)
	parser.add_argument('-e', default=defEnd, help='endDate (yyyy-mm-dd)')
	args = parser.parse_args()
	init(args)
	
	
