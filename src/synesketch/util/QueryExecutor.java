package synesketch.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class QueryExecutor {

	/**
	 * Executes SPARQL SELECT query over a given model and returns a list of values
	 * for the given variable name. This method can only be applied if SELECT query 
	 * retrieves one variable value.
	 * 
	 * @param query
	 * @param variable
	 * @param model
	 * @return List of values for the specified variable
	 */
	public List<String> executeSelectQueryOverModel(String query,
			String variable, Model model) {
		
		// Execute the query over the model
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return executeSelectQuery(query, variable, qe);
	}
	
	/**
	 * Executes SPARQL SELECT query over a specified QueryExecution instance and 
	 * returns a list of values for the given variable name. This method can only 
	 * be applied if SELECT query retrieves one variable value.
	 * 
	 * @param query
	 * @param variable
	 * @param qe
	 * @return List of values for the specified variable
	 */
	private List<String> executeSelectQuery(String query, String variable, QueryExecution qe) {
		// Execute the query over the model
		ResultSet resultSet = qe.execSelect();
		
		List<String> results = new LinkedList<String>();
		
		// obtain results from the result set
		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.nextSolution();
			RDFNode value = solution.get(variable);
			
			if (value.isLiteral())
				results.add(((Literal) value).getLexicalForm());
			else
				results.add(((Resource) value).getURI());
		}
		qe.close();
		
		return results;
	}
	
	/**
	 * Executes SPARQL DESCRIBE query over a specified model and returns a Model instance 
	 * with triples retrieved as a result.
	 * 
	 * @param query
	 * @param model
	 * @return
	 */
	public Model executeDescribeSparqlQuery(String query, Model model) {
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		Model resultModel = qe.execDescribe();
		
		// Important - free up resources used running the query
		qe.close();
		
		return resultModel;
	}
	
	/**
	 * Executes SPARQL SELECT query over a specified SPARQL endpoint and returns a list of values
	 * for the given variable name. This method can only be applied if SELECT query retrieves 
	 * one variable value.
	 * 
	 * @param query
	 * @param variable
	 * @param sparqlEndpoint
	 * @return
	 */
	public List<String> executeSelectQueryOverSparqlEndpoint(String query,
			String variable, String sparqlEndpoint) {
		
		// Execute the query over the model
		QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		return executeSelectQuery(query, variable, qe);
	}
	
	/**
	 * Executes SPARQL SELECT query over a specified SPARQL endpoint and returns a ResultSet
	 * with results.
	 * 
	 * @param query
	 * @param sparqlEndpoint
	 * @return
	 */
	public ResultSet executeSelectSparqlQuery(String query, String sparqlEndpoint) {
		// Execute the query over the model
		QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		return qe.execSelect();
	}

	public ResultSet executeSelectSparqlQuery(String query, Model model) {
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet resultSet = qe.execSelect();
		return resultSet;
	}

}
