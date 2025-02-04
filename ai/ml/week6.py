'''
Generate orders.csv from events
'''
#
# Q1.. 2008 326 events
#		2012 176 events
#
# Q2... 2008 .. 382 events
#
# Q3 

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

def stockDropsBelow5Old(**kwargs):
	#print kwargs
	ts = kwargs['ts']
	ls_orders = []
	if (kwargs['f_symprice_yest'] >= 5.0 and kwargs['f_symprice_today']<5.0):
		ls_orders = ls_orders + [ts[i]+[ s_sym, 'BUY', 100]] # split timestamp into yyyymmdd
		ls_orders = ls_orders + [ts[i+5]+[s_sym, 'SELL', 100]]
	return ls_orders

def stockDropsBelow5(**kwargs):
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
	
def stockDropsBelowHW(**kwargs):
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

			if (f_symprice_yest >= 10.0 and f_symprice_today<10.0):			
				df_events[s_sym].ix[ldt_timestamps[i]] = 1
				ls_orders = ls_orders + [convertedLdtTimestamp[i]+[ s_sym, 'BUY', 100]] # split timestamp into yyyymmdd
				if(i+5>=len(ldt_timestamps)):
					ls_orders = ls_orders + [convertedLdtTimestamp[len(ldt_timestamps)-1]+[s_sym, 'SELL', 100]]
				else:
					ls_orders = ls_orders + [convertedLdtTimestamp[i+5]+[s_sym, 'SELL', 100]]
				
	return df_events, ls_orders	
	
def bollanger(**kwargs):
	#print kwargs
	#d_data.map(
	return (kwargs['f_symprice_yest'] >= 5.0 and kwargs['f_symprice_today']<5.0)
				

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
	
def generateGraph():
	# Plotting the results
    plt.clf()
    fig = plt.figure()
    fig.add_subplot(111)
    plt.plot(ldt_timestamps, na_component_total, alpha=0.4)
    plt.plot(ldt_timestamps, na_port_total)
    ls_names = ls_port_syms
    ls_names.append('Portfolio')
    plt.legend(ls_names)
    plt.ylabel('Cumulative Returns')
    plt.xlabel('Date')
    fig.autofmt_xdate(rotation=45)
    plt.savefig('tutorial3.pdf', format='pdf')
	
def init(args):
	dt_start = dt.datetime.strptime(args.s, dateFmt)
	dt_end = dt.datetime.strptime(args.e, dateFmt) 
	print  "Start = ",dt_start, " - ",dt_end
	ldt_timestamps = du.getNYSEdays(dt_start, dt_end, dt.timedelta(hours=16))
	#ldt_timestampsExt = du.getNYSEdays(dt_start, dt_end+dt.timedelta(days=7), dt.timedelta(hours=16))# append 7 days on to cater for future sell orders

	dataobj = da.DataAccess('Yahoo')
	ls_symbols = dataobj.get_symbols_from_list(args.sy)
	print "How many symbols? ",len(ls_symbols)
	#print ls_symbols
	ls_symbols.append('SPY')
	ls_keys = ['open', 'high', 'low', 'close', 'volume', 'actual_close']
	ldf_data = dataobj.get_data(ldt_timestamps, ls_symbols, ls_keys)
	d_data = dict(zip(ls_keys, ldf_data))

	#Find Events
	df_events, ls_orders = find_events(ls_symbols, d_data, stockBigMovement)
	#print len(ls_orders)," orders =",ls_orders
	print "Saving ",len(ls_orders)," ordersOut.csv"
	saveToFile("ordersOut.csv", ls_orders)
	print "Creating Study"
	ep.eventprofiler(df_events, d_data, i_lookback=20, i_lookforward=20,
				s_filename='MyEventStudy.pdf', b_market_neutral=True, b_errorbars=True,
				s_market_sym='SPY')

if __name__ == '__main__':
	today = dt.datetime.now()
	dateFmt = "%Y-%m-%d"
	defStartDt = today-dt.timedelta(days=365)
	defStart=defStartDt.strftime(dateFmt)
	defEnd=today.strftime(dateFmt)
	parser = argparse.ArgumentParser(description='Market Simulator.')
	parser.add_argument('-p', type=int, default=10000, help='the initial amount to invest (Default 10000)')	
	parser.add_argument('-sy', default='sp5002012', help='List of symbols')
	parser.add_argument('-s', default=defStart, help='startDate (yyyy-mm-dd)')
	parser.add_argument('-e', default=defEnd, help='endDate (yyyy-mm-dd)')
	args = parser.parse_args()
	init(args)
	
	
