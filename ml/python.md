# Diff between split and rsplit.. rsplit goes from right , but then reverses order to match split.
print("Hello world ".split(" ",1)) # ['Hello', 'world ']
print("Hello world ".rsplit(" ",1)) # ['Hello world', '']
print("seatmap:UDAP-QVRB-FTCZ-DLCC:VIP:FX".rsplit(":",2))