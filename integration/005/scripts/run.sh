testname=$1
branch=$2
version=${3:-$branch} 

test="secret ping"


if [ -z "$test" ];
then
    echo "[Error]: Secret function needed!"

elif [ -z "$1" ];
then
    echo "[Error]: Test name parameter required!"

elif [ -z "$2" ];
then
    echo "[Error]: Branch name required!"

else 
    source setup/setup.sh
    source setup/create-chain.sh
    if [ $testname -eq 01 ];
    then 
	source setup/setup-pyro.sh
        source tests/test01-integration.sh
    elif [ $testname -eq 02 ];
    then
	source setup/setup-pyro.sh
        source tests/test02-atlasfull.sh
    elif [ $testname -eq 03 ];
    then
        source setup/setup-pyro.sh
        source tests/test03-hist-catalogue.sh
    elif [ $testname -eq 04 ];
    then
        source tests/test04-query-loop.sh
    else 
        source setup/setup-pyro.sh
        source tests/$testname
    fi 
fi

