/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.jpamodelgen.test.elementcollection;

import org.hibernate.jpamodelgen.test.util.CompilationTest;
import org.hibernate.jpamodelgen.test.util.TestForIssue;
import org.hibernate.jpamodelgen.test.util.WithClasses;
import org.hibernate.jpamodelgen.test.util.WithMappingFiles;
import org.junit.Test;

import static org.hibernate.jpamodelgen.test.util.TestUtil.assertMapAttributesInMetaModelFor;
import static org.hibernate.jpamodelgen.test.util.TestUtil.assertMetamodelClassGeneratedFor;
import static org.hibernate.jpamodelgen.test.util.TestUtil.assertNoSourceFileGeneratedFor;

/**
 * @author Hardy Ferentschik
 */
public class ElementCollectionTest extends CompilationTest {

	@Test
	@TestForIssue(jiraKey = "METAGEN-8")
	@WithClasses({ House.class, Room.class })
	public void testElementCollectionOnMap() {
		assertMetamodelClassGeneratedFor( House.class );
		assertMetamodelClassGeneratedFor( Room.class );
		// side effect of METAGEN-8 was that a meta class for String was created!
		assertNoSourceFileGeneratedFor( String.class );
	}

	@Test
	@TestForIssue(jiraKey = "METAGEN-19")
	@WithClasses({ Hotel.class, Room.class, Cleaner.class })
	public void testMapKeyClass() {
		assertMetamodelClassGeneratedFor( Hotel.class );
		assertMapAttributesInMetaModelFor(
				Hotel.class, "roomsByName", String.class, Room.class, "Wrong type in map attribute."
		);

		assertMapAttributesInMetaModelFor(
				Hotel.class, "cleaners", Room.class, Cleaner.class, "Wrong type in map attribute."
		);
	}

	@Test
	@TestForIssue(jiraKey = "METAGEN-22")
	@WithClasses({ Hostel.class, Room.class, Cleaner.class })
	@WithMappingFiles("hostel.xml")
	public void testMapKeyClassXmlConfigured() {
		assertMetamodelClassGeneratedFor( Hostel.class );
		assertMapAttributesInMetaModelFor(
				Hostel.class, "roomsByName", String.class, Room.class, "Wrong type in map attribute."
		);

		assertMapAttributesInMetaModelFor(
				Hostel.class, "cleaners", Room.class, Cleaner.class, "Wrong type in map attribute."
		);
	}
}
