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
catalogue=$1

if [ "$catalogue" != "DEFAULT" ]
  then
    echo "Building TAP service for the supplied catalogue:" + ${catalogue:?}
  else
    catalogue=${testrundatabase:?}
    echo "No catalogue given.. Using catalogue from config:" + ${catalogue:?}
fi

source ${HOME:?}/chain.properties

setupdir="${HOME:?}/setup"

chcon -t svirt_sandbox_file_t "${setupdir:?}/build-tap.sh" 

chmod a+r "${setupdir:?}/build-tap.sh"

echo "*** Running tap setup script ***"

# -----------------------------------------------------
# Start our test container.
#[user@desktop]

    source "${HOME:?}/chain.properties"
    docker run \
        -it \
        --memory 512M \
        --volume "${setupdir:?}/build-tap.sh:${setupdir:?}/build-tap.sh" \
        --env "datadata=${datadata:?}" \
        --env "datalink=${datalink:?}" \
        --env "datauser=${datauser:?}" \
        --env "datapass=${datapass:?}" \
        --env "datadriver=${datadriver:?}" \
        --env "metadataurl=jdbc:jtds:sqlserver://${userlink:?}" \
        --env "metauser=${metauser:?}" \
        --env "metapass=${metapass:?}" \
        --env "metadata=${metadata?}" \
        --env "endpointurl=http://${firelink:?}:8080/firethorn" \
        --env "catalogue=${catalogue:?}" \
        --link "${firename:?}:${firelink:?}" \
        "firethorn/tester:1.1" \
        bash -C ${setupdir:?}/build-tap.sh

