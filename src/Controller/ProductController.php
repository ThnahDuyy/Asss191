<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ItemsRepository;

class ProductController extends AbstractController
{
    #[Route('/product', name: 'product')]
    public function index(ItemsRepository $itemsRepository): Response
    {
        return $this->render('product/index.html.twig', [
            'items' => $itemsRepository->findAll(),
        ]);
    }
}
