package stv6.templating;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethods;
import org.checkerframework.checker.mustcall.qual.InheritableMustCall;
import org.checkerframework.checker.mustcall.qual.Owning;
import stv6.http.request.variables.Variable;
import stv6.http.request.variables.VariableList;
import stv6.templating.environment.Environment;
import stv6.templating.environment.GlobalEnvironment;
import stv6.templating.environment.ObjectManager;

public class Templator {
	
	public Templator() {
		
	}

	public Template newTemplate(String path) throws FileNotFoundException {

		return new Template( new File(path) );
	}

	@InheritableMustCall({"close"})
	public static class Template {
		@Owning
		private final TemplateReader file;
		
		private final ObjectManager objects;
		private final VariableList globalVars;
		
		private Template(File source) throws FileNotFoundException {
			objects = new ObjectManager();
			globalVars = new VariableList();
			file = new TemplateReader(source);
		}		
		
		public void putObject( TemplateObject obj ) {
			objects.put(obj);			
		}
		
		/**
		 * Set a global variable
		 * 
		 * @param v
		 */
		public void putVariable(Variable v) {
			globalVars.put(v);
		}

		public void putVariable(String name, String value) {
			globalVars.put(new Variable(name, value));
		}

		public void putVariable(String name, int value) {
			globalVars.put(name, value);
		}
		
		public void writeTo(Appendable out) {
			GlobalEnvironment env = new GlobalEnvironment(globalVars, objects);
			doTemplateLoop(file, env, out);
		}
		
		public static void doTemplateLoop(TemplateReader tpl, Environment env, Appendable out) {
			StringBuilder line;
			while (true) {
				try {
					line = tpl.next();
					
					if (line == null)
						break;
					
					CodeHandlerManager.getInstance().handle( 
							line, tpl, env, out);
					
				} catch (EOFException e) {
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				} 
			}
		}

		@EnsuresCalledMethods(value = {"file"} , methods = {"close"})
		public void close(){
            file.close();
        }
	}
}
