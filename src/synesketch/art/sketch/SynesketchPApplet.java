/**
 * Synesketch 
 * Copyright (C) 2008  Uros Krcadinac
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package synesketch.art.sketch;

import processing.core.PApplet;
import synesketch.*;
import synesketch.art.util.MappingUtility;

public class SynesketchPApplet extends PApplet {
	
	private static final long serialVersionUID = 1L;

	int dim = 500;
	
	private MappingUtility mappingManager;
	
	public SynesketchPApplet() throws Exception {		
		super();
		mappingManager = MappingUtility.getInstance(this);
	}
	
	public SynesketchPApplet(int dim) throws Exception {
		super();
		mappingManager = MappingUtility.getInstance(this);
	}
	
	public void setup() {
		
		size(dim, dim, P3D);
		background(255);
		noStroke();
		
		mappingManager.loadVisualizations();
	}
	
	public void synesketchUpdate(SynesketchState state) {
		mappingManager.update(state);
	}
	
	public void draw() { 
		mappingManager.draw();
	}
	
}