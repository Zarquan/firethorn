package tap.resource;

/*
 * This file is part of TAPLibrary.
 * 
 * TAPLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TAPLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with TAPLibrary.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2011 - UDS/Centre de Données astronomiques de Strasbourg (CDS)
 */

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tap.ADQLExecutor;
import tap.ServiceConnection;
import tap.TAPException;
import tap.Uws4Tap;
import uws.UWSException;
import uws.job.JobList;

public class ASync implements TAPResource {

	public static final String RESOURCE_NAME = "async";

	@SuppressWarnings("unchecked")
	protected final ServiceConnection service;
	protected final Uws4Tap uws;

	@SuppressWarnings("unchecked")
	public ASync(ServiceConnection service) throws UWSException {
		this.service = service;
		uws = new Uws4Tap(this.service);
		uws.addJobList(new JobList<ADQLExecutor>(getName()));
	}

	@Override
	public String getName() { return RESOURCE_NAME; }

	@Override
	public void setTAPBaseURL(String baseURL) { ; }

	public final Uws4Tap getUWS(){
		return uws;
	}

	@Override
	public void init(ServletConfig config) throws ServletException { ; }

	@Override
	public void destroy() { ; }

	@Override
	public boolean executeResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TAPException {
		try {
			return uws.executeRequest(request, response);
		} catch (UWSException e) {
			throw new TAPException(e);
		}
	}

}
