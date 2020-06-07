package org.FF.GUI.views;


public class CalculateBanknotes {
	private static int totaal10;
	private static int totaal20;
	private static int totaal50;
	private int totaal;
	private final static int hardlimiet = 5;
	private int rest = -1;
	private int newRest = -1;


	/**
	 * calculates amount of banknotes available
	 * 
	 * @param totaal
	 * @param option
	 * @param bankNotes_10
	 * @param bankNotes_20
	 * @param bankNotes_50
	 * @return
	 */
	public int[] calculateBanknotesTotaal(int totaal, int option, int bankNotes_10, int bankNotes_20, int bankNotes_50) {
		totaal10 = bankNotes_10;
		totaal20 = bankNotes_20;
		totaal50 = bankNotes_50;
		
		this.totaal = totaal;
		int array[] = { 0, 0, 0 };

		int limiet1 = totaal / 10;
		if (limiet1 > hardlimiet)
			limiet1 = hardlimiet;
		if (limiet1 > totaal10)
			limiet1 = totaal10;

		int limiet2 = totaal / 20;
		if (limiet2 > hardlimiet)
			limiet2 = hardlimiet;
		if (limiet2 > totaal20)
			limiet2 = totaal20;

		int limiet3 = totaal / 50;
		if (limiet3 > hardlimiet)
			limiet3 = hardlimiet;
		if (limiet3 > totaal50)
			limiet3 = totaal50;

		if (totaal > (limiet1 * 10 + limiet2 * 20 + limiet3 * 50))
			return null;

		rest = -1;
		newRest = -1;
		int biggestNumber = 0;

		if (option == 1) {
			mostlyBankNotes10(array, limiet1, limiet2, limiet3);
		}

		else if (option == 2) {
			mostlyBankNotes20(array, limiet1, limiet2, limiet3);
		} 
		else if (option == 3) {
			mostlyBankNotes50(array, limiet1, limiet2, limiet3);

		} else if (option == 4) {
			biggestNumber = biggestNumber(limiet1, limiet2, limiet3);
			if(biggestNumber == 10) {
				mostlyBankNotes10(array, limiet1, limiet2, limiet3);
			}else if(biggestNumber == 20) {
				mostlyBankNotes20(array, limiet1, limiet2, limiet3);
			}else if(biggestNumber == 50) {
				mostlyBankNotes50(array, limiet1, limiet2, limiet3);
			}
		}
		
		return array;

	}
	
	/**
	 * 
	 * Calculates how many banknotes needed if you want mostly banknotes of 50
	 * 
	 * @param array
	 * @param limiet1
	 * @param limiet2
	 * @param limiet3
	 * @return
	 */

	private int[] mostlyBankNotes50(int array[], int limiet1, int limiet2, int limiet3) {
		rest = totaal - limiet3 * 50;
		array[2] = limiet3;
		if (rest == 0) {
			return array;
		}

		int biggestNumber = biggestNumber(totaal10, totaal20, 0);

		if (biggestNumber == 10) {
			newRest = rest / 10;
			
			if(newRest > limiet1) {
				newRest = limiet1;
				array[0] = newRest-1;
				rest -= (newRest-1)*10;
				array[1] = rest/20;
				rest-=array[1]*20;
				
				if(rest == 10) {
					array[0]+= 1;
					rest-= 10;
				}
				
			
				if (rest == 0) {
					return array;
				}
			}
			
			array[0] = newRest;
		} else {
			newRest = rest / 20;
			
			if(newRest > limiet2) {
				newRest = limiet2;
				array[1] = newRest;
				rest -= newRest*20;
				array[0] = rest/10;
				
			} else {
				rest = rest % 20;
				array[1] = newRest;

				array[0] = rest / 10;
				rest = rest - array[0]*10;
			}
			
			if (rest == 0) {
				return array;
			}
		}

		if (rest == 0) {
			return array;
		}
		return null;
	}
	
	/**
	 * Calculates how many banknotes needed if you want mostly banknotes of 20
	 * 
	 * @param array
	 * @param limiet1
	 * @param limiet2
	 * @param limiet3
	 * @return
	 */
	private  int[] mostlyBankNotes20(int array[], int limiet1, int limiet2, int limiet3) {
		rest = totaal - limiet2 * 20;
		array[1] = limiet2;
		if (rest == 0) {
			return array;
		}
		int biggestNumber = biggestNumber(totaal10, 0, totaal50);

		if (biggestNumber == 50) {
			newRest = rest / 50;

			if (newRest < limiet3) {

				array[2] = newRest;
				rest -= 50 * newRest;

				switch (rest) {
				
				case 10:
					if(limiet1 > 0) {
						array[0] += 1;
					}else if(totaal50 >= 6) {
						array[1] -= 2;
						array[2] += 1;
					}
					rest -= 10;
					break;
				case 20:
					
					if(limiet1 >= 2) {
						array[0] += 2;
						
					}else if(totaal20 >= 6) {
						array[1]+= 1;
					}else if(limiet3 >= 4){
						array[1] -= 4;
						array[3] += 2;
					}
					rest -= 20;
					
					break;

				case 30: 
					if (totaal20 >= 6 && limiet1 >= 1) {
						array[1] += 1;
						array[0] += 1;
					} else if(limiet1  >= 3 ) {
						array[0] += 3;
					}else if(limiet3 >= 1 && limiet1 >= 1) {
						array[1] -= 1;
						array[2] += 1;
						array[0] += 1;
					}

					rest -= 30;
					break;
				case 40:
					if (totaal20 >= limiet2 + 2) {
						array[1] += 2;
					}else if(totaal20 >= limiet2+1 && limiet1 >= 2 ) {
						array[1] += 1;
						array[0] += 2;
					}else {
						array[2] += 1;
						array[1] -= 1;
						array[0] += 1;
					}

					rest -= 40;
					break;
				default:
				}
			}
		} else {
			newRest = rest / 10;

			if (newRest < limiet1 || limiet3 == 0) {
				array[0] = newRest;
				newRest = rest % 10;

				rest = newRest * 10;
			} else {
				newRest = rest / 50;

				if (newRest < limiet3) {

					array[2] = newRest;
					newRest = rest % 50;

					switch (newRest) {
					case 10:
						array[0] += 1;

						newRest -= 10;
						break;
					case 20:
						array[0] += 2;
						newRest -= 20;
						break;

					case 30:
						if (totaal20 >= array[1] + 1) {
							array[1] += 1;
							array[0] += 1;
						} else {
							array[0] += 3;
						}

						newRest -= 30;
						break;
					case 40:
						if (totaal20 >= array[1] + 2) {
							array[1] += 2;
						}

						if (totaal20 >= array[1] + 1) {
							array[1] += 1;
							array[0] += 2;
						}

						newRest -= 40;
						break;
					default:
					}
				}
			}
		}

		if (rest == 0) {
			return array;
		}
		return null;
	}
	
	/**
	 * Calculates how many banknotes needed if you want mostly banknotes of 10
	 * @param array
	 * @param limiet1
	 * @param limiet2
	 * @param limiet3
	 * @return
	 */

	private int[] mostlyBankNotes10(int array[], int limiet1, int limiet2, int limiet3) {
		rest = totaal - limiet1 * 10;
		array[0] = limiet1;
		if (rest == 0) {
			return array;
		}

		int biggestNumber = biggestNumber(0, totaal20, totaal50);

		if (biggestNumber == 20) {
			newRest = rest / 20;

			if (newRest < limiet2 || limiet3 == 0) {
				array[1] = newRest;
				rest -= newRest*20;
				if (rest == 10) {
					array[0] -= 1;
					array[1] += 1;
					newRest -= 10;
				}

				if (rest == 0) {
					return array;
				}
			}

			newRest = rest / 50;

			if (newRest < limiet3) {

				array[2] = newRest;
				newRest = rest % 50;

				switch (newRest) {
				case 10:
					if (totaal10 >= array[0] + 1) {
						array[0] += 1;
					} else {
						array[0] -= 1;
						array[1] += 1;
					}
					newRest -= 10;
					break;

				case 20:
					array[1] += 1;
					newRest -= 20;
					break;

				case 30:
					if (totaal10 >= array[0] + 1) {
						array[0] += 1;
						array[1] += 1;
					} else {
						array[0] -= 1;
						array[1] += 2;
					}
					newRest -= 30;
					break;

				case 40:
					array[1] += 2;
					newRest -= 40;
					break;

				default:
				}
			}

			if (newRest == 0) {
				return array;
			}

		} else {
			newRest = rest / 50;

			if (newRest < limiet3) {

				array[2] = newRest;
				newRest = rest % 50;

				switch (newRest) {
				case 10:
					if (totaal10 >= array[0] + 1) {
						array[0] += 1;
					} else {
						array[0] -= 1;
						array[1] += 1;
					}
					newRest -= 10;
					break;
				case 20:
					array[1] += 1;
					newRest -= 20;
					break;

				case 30:
					if (totaal10 >= array[0] + 1) {
						array[0] += 1;
						array[1] += 1;
					} else {
						array[0] -= 1;
						array[1] += 2;
					}

					newRest -= 30;
					break;
				case 40:
					array[1] += 2;
					newRest -= 40;
					break;
				default:
				}
			}

			if (newRest == 0) {
				return array;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */

	private static int biggestNumber(int a, int b, int c) {
		if (a > b && a > c) {
			return 10;
		} else if ( (b > a && b > c) || (a == b) ) {
			return 20;
		} else if (c > a && c > b) {
			return 50;
		} else if(c == a || c == b) {
			return 50;
		}
		return -1; 
		
	}
	
}
