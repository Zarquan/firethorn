#!/bin/bash -eu
# -e: Exit immediately if a command exits with a non-zero status.
# -u: Treat unset variables as an error when substituting.
#
#  Copyright (C) 2013 Royal Observatory, University of Edinburgh, UK
#
#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#

testname=$1
branch=$2
version=${3:-$branch}
input_variable=""
if [ -n "$4" ]; then
input_variable=${4}
fi

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
    elif [ $testname -eq 05 ];
    then
	source setup/setup-pyro.sh
        source tests/test05-integration-json.sh
    elif [ $testname -eq 06 ];
    then
        source setup/setup-pyro.sh 
        if [  -n "$input_variable" ]
        then 
            echo "Running tap test with: " + ${input_variable:?}
            source tests/test06-taptest.sh  ${input_variable:?}
        else
            echo -n "Please enter a TAP service and press [ENTER]: "
            read input_variable
            source tests/test06-tapstress.sh  ${input_variable:?}
        fi
    elif [ $testname -eq 07 ];
    then
        source setup/setup-pyro.sh 
        if [  -n "$input_variable" ]
        then 
            echo "Running tap test with: " + ${input_variable:?}
            source tests/test07-fulltaptest.sh  ${input_variable:?}
        else
            echo -n "Please enter a TAP service and press [ENTER]: "
            read input_variable
            source tests/test07-fulltaptest.sh  ${input_variable:?}
        fi
	
        
        
    elif [ $testname -eq 08 ];
    then
        if [  -n "$input_variable" ]
        then 
            echo "Running setup tap script with: " + ${input_variable:?}
            source setup/setup-tap.sh ${input_variable:?}
        else
            echo -n "Please enter a catalogue and press [ENTER]: "
            read input_variable
            if [  -n "$input_variable" ]
            then 
       	        source setup/setup-tap.sh ${input_variable:?}
            else
                source setup/setup-tap.sh "DEFAULT"
            fi
        fi
    else 
        source setup/setup-pyro.sh
        source tests/$testname
    fi 
fi


