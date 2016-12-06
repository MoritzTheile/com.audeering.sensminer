package com.audeering.sensminer.model.abstr;


public interface CRUDService<D extends AbstrDTO, F extends FetchQuery> {

	/**
	 * 
	 * @param page
	 *            Defines the page of data. If null all available data is returned.
	 * @param query
	 *            Defines the dtos to be returned.
	 * 
	 */
	DTOFetchList<D> fetchList(
			Page page,
			F query
			);

	/**
	 * 
	 * @param dtoId
	 *            The id of the dto to be returned.
	 * 
	 */
	D get(String dtoId);

	/**
	 * Updates the given dto. Id must be given.
	 * 
	 * @param dto
	 * 
	 */
	D update(
			D dto
	);

	/**
	 * Creates the given dto.
	 *
	 * @param dto
	 */
	D create(
			D dto
	);



	/**
	 * 
	 * @param dtoId
	 * 
	 */
	void delete(
			String dtoId
			);


}