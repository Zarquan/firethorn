#!/bin/sh
#
# Copyright (c) 2016, ROE (http://www.roe.ac.uk/)
# All rights reserved.
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

#
# Strict error checking.
# http://redsymbol.net/articles/unofficial-bash-strict-mode/
set -euo pipefail
IFS=$'\n\t'

#
# Install directory
: ${servercode:=/var/local/hsqldb}

#
# Check our install directory.
echo "Checking code path [${servercode}]"
if [ ! -e "${servercode}" ]
then
    echo "Creating code path [${servercode}]"
    mkdir -p "${servercode}"
fi

#
# Extra version fragment in the URL.
urlversion=$(echo "${hsqldbversion}" | cut --fields '1,2' --delimiter '.' --output-delimiter '_')

#
# Use a temp directory.
tempdir=$(mktemp -d)
pushd "${tempdir:?}"

    echo ""
    echo "Downloading zip files"
    wget  --quiet "http://downloads.sourceforge.net/project/hsqldb/hsqldb/hsqldb_${urlversion}/hsqldb-${hsqldbversion}.zip"

    #
    # Verify the signature.
    # SHA1 f5fce3d5eb21294f9ffba40c34e7c736ab64d6b9
    # MD5  62c0b97e94fe47d5e50ff605d2edf37a

    #
    # Unpack the zip file.
    unzip \
        -d "${servercode}" \
        hsqldb-${hsqldbversion}.zip

popd



