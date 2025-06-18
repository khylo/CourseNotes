#!/bin/bash
# Parse command line arguments
while [[ $# -gt 0 ]]; do
    key="$1"

    case $key in
        --salary)
        salary="$2"
        shift
        shift
        ;;
        --age)
        age="$2"
        shift
        shift
        ;;
        --taxcredit)
        taxcredit="$2"
        shift
        shift
        ;;
        --status)
        status="$2"
        shift
        shift
        ;;
        *)
        echo "Unknown option: $key"
        exit 1
        ;;
    esac
done
#echo "Input params $salary&age=$age&taxCredit=$taxcredit&status=$status"
# Download the HTML using curl and extract the yearly values for Gross, tax, usc, prsi, and net pay
html=$(curl -s 'https://ie.thesalarycalculator.co.uk/salary.php' \
-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,/;q=0.8,application/signed-exchange;v=b3;q=0.7' \
-H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36' \
--data-raw "salary=$salary&status=0&age=low&pension=&taxCredit=3550&taxallowance=&chosenTaxYear=2023&submit=Go%21&timeperiods%5B%5D=1&timeperiods%5B%5D=12&timeperiods%5B%5D=52&timeperiods%5B%5D=260&submit=" \
--compressed)
# Cut removes the problem euro symbol.   tr remove the comma in the number formating
gross=$(echo "$html" | pup 'tr.gross.normal td:nth-child(2) text{}' | cut -c 8- | tr -d ',')
tax=$(echo "$html" | pup 'tr.tax.normal td:nth-child(2) text{}' | cut -c 8- | tr -d ',')
usc=$(echo "$html" | pup 'tr.USC.normal td:nth-child(2) text{}' | cut -c 8- | tr -d ',')
prsi=$(echo "$html" | pup 'tr.PRSI.grey td:nth-child(2) text{}' | cut -c 8- | tr -d ',')
net_pay=$(echo "$html" | pup 'tr.takehome.normal td:nth-child(2) text{}' | cut -c 8- | tr -d ',')

# Print the extracted values
#printf "Gross: %s\n" "$gross"
#printf "Tax: %s\n" "$tax"
#printf "USC: %s\n" "$usc"
#printf "PRSI: %s\n" "$prsi"
#printf "Net Pay: %s\n" "$net_pay"
#echo "salary,gross,tax,usc,prsi,net_pay"
echo "$salary,$gross,$tax,$usc,$prsi,$net_pay"